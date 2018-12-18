package com.xuzp.insuredxmltool.template;

import com.google.common.collect.Lists;
import com.xuzp.insuredxmltool.constants.TemplateConstant;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin Xu on 2018/12/15.
 */
@Slf4j
public class 模板 {

    private static Configuration cfg = null;

    static {
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        cfg.setDefaultEncoding(TemplateConstant.ENCODING);
        StringTemplateLoader loader = new StringTemplateLoader();
        Lists.newArrayList(TemplateConstant.SAMPLE_FTL)
                .stream().forEach(templateName -> {
            String  content = getDefaultTemplateContent(templateName);
            loader.putTemplate(templateName, content);
        });
        cfg.setTemplateLoader(loader);
    }

    private static String getDefaultTemplateContent(String templateName) {
        try (InputStream in = 模板.class.getClassLoader().getResourceAsStream(TemplateConstant.TEMPLATE_PATH
                + "/" + templateName);) {
            return IOUtils.toString(in, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("加载默认模板失败，模板名={},异常={}", templateName, e.getMessage());
        }
        return null;
    }

    public static String loadTemplate(String templateName, Map<String, Object> params) {
        try {
            Template template = cfg.getTemplate(templateName);
            StringWriter out = new StringWriter();
            template.process(params, out);
            return out.toString();
        } catch (Exception e) {
            log.error("获取模板内容失败, 模板={}，参数={}，异常={}", templateName, params, e);
            return null;
        }
    }

    public static void main(String[] args) {

        log.info(loadTemplate(TemplateConstant.SAMPLE_FTL, new HashMap<String, Object>(){
            {
                put("内部标识", "ZY000016");
                put("公司编码", "generali");
                put("保险编码", "16050");
                put("保险名称", "横琴金禧年年保险");
                put("保险简称", "金喜年年");
                put("计算单位", "10000");
                put("保险类别", "life");
                put("保险次序", "1000");
                put("输入类目", "AMOUNT");
                put("交费方式列表", new ArrayList<String>(){
                    {
                        add("single");
                        add("year");
                    }
                });
                put("交费年期列表", new ArrayList<String>(){
                    {
                        add("single");
                        add("term_3");
                        add("term_5");
                        add("term_10");
                        add("term_20");
                        add("term_30");
                    }
                });
                put("保险期间列表", new ArrayList<String>(){
                    {
                        add("to_full");
                    }
                });
            }
        }));
    }
}
