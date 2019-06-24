package com.qilin.mall.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.api.dom.java.Field;

import java.util.Properties;

/**
 * 自定义注释生成器
 */
public class CommentGenerator extends DefaultCommentGenerator {


    private boolean addRemarkComments = false;

    private static final String EXAMPLE_SUFIX = "Example";

    private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME = "io.swagger.annotations.ApiModelProperty";


    /*
    CommentGenerator 为 Mybatis Generator 的自定义注释生成器，修改 addFieldComment 方法使其生成 Swagger 的
    @ApiModelProperty 注解来取代原来的方法注释， 添加 addJavaFileComment 方法，使其能在 import 中导入 @ApiModelProperty
    否则需要手动导入该类，再需要大量实体类时，是一件非常麻烦的事情。
     */

    @Override
    public void addConfigurationProperties(Properties properties){
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }


    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn instrospectedColumn){
        String remarks = instrospectedColumn.getRemarks();

        //根据参数和备注信息判断是否添加备注信息
        if(addRemarkComments && StringUtility.stringHasValue(remarks)){
//            addFieldJavaDoc(field, remarks);

            // 数据库中特殊字符需要转义
            if(remarks.contains("\"")){
                remarks = remarks.replace("\"","'");
            }

            //给model 的字段添加 swagger 注解
            field.addJavaDocLine("@ApiModelProperty(value=\"" + remarks + "\")");
        }
    }


    private void addFieldJavaDoc(Field field, String remarks){
        //文档注释开始
        field.addJavaDocLine("/**");
        //获取数据库字段的备注信息
        String[] remarklines = remarks.split(System.getProperty("line.separator"));
        for(String remarkLine : remarklines){
            field.addJavaDocLine(" * " + remarklines);
        }

        addJavadocTag(field, false);
        field.addJavaDocLine(" */");
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit){
        super.addJavaFileComment(compilationUnit);
        if(!compilationUnit.isJavaInterface() && !compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFIX)){
            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));
        }
    }
}
