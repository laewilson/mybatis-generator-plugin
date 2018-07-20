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
 * @description rename the xml method 
 * @author Wilson Lai<br>
 * @version 1.0 <br>
 * @Since 2017年7月27日
 * 
 */
public class RenameXMLMethodPlugin extends PluginAdapter {

  
    private String renameOfInsert;
    private String renameOfUpdateByPrimaryKeySelective;
    private String renameOfDeleteByPrimaryKey;
   
    /**
     * @attention the validate should be always true
     *  the mehod is for initialize parameters
     */
    @Override
    public boolean validate(List<String> warnings) {
       
        
        renameOfInsert = properties.getProperty("renameOfInsert");
        renameOfInsert = stringHasValue(renameOfInsert) 
                ? renameOfInsert : "getById";
        
        renameOfDeleteByPrimaryKey =properties.getProperty("renameOfDeleteByPrimaryKey");
        renameOfDeleteByPrimaryKey = stringHasValue(renameOfDeleteByPrimaryKey) 
                ? renameOfDeleteByPrimaryKey : "delete";
        
        renameOfUpdateByPrimaryKeySelective = properties.getProperty("renameOfUpdateByPrimaryKeySelective");
        renameOfUpdateByPrimaryKeySelective = stringHasValue(renameOfUpdateByPrimaryKeySelective) ?
                renameOfUpdateByPrimaryKeySelective: "update";
        
        // 
        return true;
    }
    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        
        
       /* //control whether is the method generate
        TableConfiguration tc = introspectedTable.getTableConfiguration();
        tc.setInsertSelectiveStatementEnabled(false);
        tc.setUpdateByPrimaryKeyStatementEnabled(false);
        tc.setUpdateByPrimaryKeySelectiveStatementEnabled(true);*/
       
        // rename the method 
        introspectedTable.setSelectByPrimaryKeyStatementId(renameOfInsert);
        introspectedTable.setDeleteByPrimaryKeyStatementId(renameOfDeleteByPrimaryKey);
        introspectedTable.setUpdateByPrimaryKeySelectiveStatementId(renameOfUpdateByPrimaryKeySelective);
        
        
        List<GeneratedXmlFile> files = introspectedTable.getGeneratedXmlFiles();

        return files;

    }
   
}
