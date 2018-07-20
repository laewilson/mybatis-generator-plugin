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
public class EnableXMLMethodPlugin extends PluginAdapter {

    private boolean insertStatementEnabled = true;

    private boolean insertSelectiveStatementEnabled = true;

    private boolean updateByPrimaryKeySelectiveStatementEnabled = true;

    private boolean selectByPrimaryKeyStatementEnabled = true;

    private boolean selectByExampleStatementEnabled = false;

    private boolean updateByPrimaryKeyStatementEnabled =false;

    private boolean deleteByPrimaryKeyStatementEnabled = true;

    private boolean deleteByExampleStatementEnabled = false;

    private boolean countByExampleStatementEnabled = false;

    private boolean updateByExampleStatementEnabled = false;
    
    private boolean generateClientJavaMethodEnabled = false;

    /**
     * @attention the validate should be always true the mehod is for initialize
     *            parameters
     */
    @Override
    public boolean validate(List<String> warnings) {

        insertStatementEnabled = setBoolProperty("insertStatementEnabled", insertStatementEnabled);

        insertSelectiveStatementEnabled = setBoolProperty("insertSelectiveStatementEnabled",
                insertSelectiveStatementEnabled);

        updateByPrimaryKeySelectiveStatementEnabled = setBoolProperty("updateByPrimaryKeySelectiveStatementEnabled",
                updateByPrimaryKeySelectiveStatementEnabled);

        selectByPrimaryKeyStatementEnabled = setBoolProperty("selectByPrimaryKeyStatementEnabled",
                selectByPrimaryKeyStatementEnabled);
          //
        selectByExampleStatementEnabled = setBoolProperty("selectByExampleStatementEnabled",
                selectByExampleStatementEnabled);
        updateByPrimaryKeyStatementEnabled = setBoolProperty("updateByPrimaryKeyStatementEnabled",
                updateByPrimaryKeyStatementEnabled);
        deleteByPrimaryKeyStatementEnabled = setBoolProperty("deleteByPrimaryKeyStatementEnabled",
                deleteByPrimaryKeyStatementEnabled);
        deleteByExampleStatementEnabled = setBoolProperty("deleteByExampleStatementEnabled",
                deleteByExampleStatementEnabled);
        countByExampleStatementEnabled = setBoolProperty("countByExampleStatementEnabled",
                countByExampleStatementEnabled);
        updateByExampleStatementEnabled = setBoolProperty("updateByExampleStatementEnabled",
                updateByExampleStatementEnabled);
        generateClientJavaMethodEnabled = setBoolProperty("generateClientJavaMethodEnabled",
                generateClientJavaMethodEnabled);
        
        //
        return true;
    }

    
    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {

        // control whether is the method generate
        TableConfiguration tc = introspectedTable.getTableConfiguration();
        
        tc.setInsertStatementEnabled(insertStatementEnabled);
        tc.setInsertSelectiveStatementEnabled(insertSelectiveStatementEnabled);
        tc.setUpdateByPrimaryKeyStatementEnabled(updateByPrimaryKeyStatementEnabled);
        tc.setUpdateByPrimaryKeySelectiveStatementEnabled(updateByPrimaryKeySelectiveStatementEnabled);
        
        tc.setGenerateClientJavaMethodEnabled(generateClientJavaMethodEnabled);
        
       
        
        
        
        List<GeneratedXmlFile> files = introspectedTable.getGeneratedXmlFiles();

        return files;

    }
    private boolean isValid(String propertyName) {
        
        return stringHasValue(properties.getProperty(propertyName));
    }
    private boolean setBoolProperty(String propertyName,boolean origin) {
        
        if (isValid(propertyName)) {
            return Boolean.parseBoolean(properties.getProperty(propertyName)); 
        }
       
        return origin;
    }
}
