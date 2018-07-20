package com.kingpointcn.mybatis.generator.plugins;

import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.TableConfiguration;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @description add selectList method to xml files
 * @author Wilson Lai<br>
 * @version 1.0 <br>
 * @Since 2017年7月27日
 * 
 */
public class AddSelectListPlugin extends PluginAdapter {

    private String addSelectListMethod;
    
    private String selectListMethodName;
    
    private boolean bAddSelectListMethod;//flag to add additional selectList method
    
    /**
     * @attention the validate should be always true
     *  the mehod is for initialize parameters
     */
    @Override
    public boolean validate(List<String> warnings) {
        addSelectListMethod = properties.getProperty("addSelectListMethod");
        bAddSelectListMethod = stringHasValue(addSelectListMethod) ? Boolean.parseBoolean(addSelectListMethod)
                : true;
        if (bAddSelectListMethod) {
            selectListMethodName = properties.getProperty("selectListMethodName");
            selectListMethodName = stringHasValue(selectListMethodName) ? selectListMethodName : "selectList";
        }
        // 
        return true;
    }
    
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        if (bAddSelectListMethod) {
            addSelectListElement(document, introspectedTable);
        }
         
         
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    public void addSelectListElement(Document document, IntrospectedTable introspectedTable) {
        XmlElement select = new XmlElement("select");//method element
        select.addAttribute(new Attribute("id", selectListMethodName));//id
        context.getCommentGenerator().addComment(select);//MGB comment
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        select.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        //--------------the real SQL generate--------------------------------
        select.addElement(new TextElement(" select "));
        select.addElement(getBaseColumnListElement(introspectedTable));
        select.addElement(new TextElement(" from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        select.addElement(new TextElement(" where "));
        
        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : ListUtilities.removeGeneratedAlwaysColumns(introspectedTable
                .getNonPrimaryKeyColumns())) {
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
           
            
            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));
            sb.append(" and ");

            isNotNullElement.addElement(new TextElement(sb.toString()));
            select.addElement(isNotNullElement);
        }
        select.addElement(new TextElement(" 1=1 "));
        //-----------------------------------------------------------------------------
        
        XmlElement parentElement = document.getRootElement();
        parentElement.addElement(select);

    }

    protected XmlElement getBaseColumnListElement(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("include"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getBaseColumnListId()));
        return answer;
    }

}
