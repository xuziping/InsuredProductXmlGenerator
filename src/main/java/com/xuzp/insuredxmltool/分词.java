package com.xuzp.insuredxmltool;

import com.google.common.collect.Lists;
import com.xuzp.insuredxmltool.enums.IEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.poi.ss.formula.functions.T;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.StringReader;
import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/12/19
 * @Time 18:45
 */
@Slf4j
public class 分词 {

    public static List<String> matchList(String line, IEnum<T>[] enums) {
        List<String> words = startIKAnalyzer(line);
        List<String> ret = null;
        for (String w : words) {
            for(IEnum<T> e: enums) {
                String code = e.getCodeByName(w);
                if (StringUtils.isNotEmpty(code)) {
                    if (ret == null) {
                        ret = Lists.newArrayList(code);
                    } else {
                        ret.add(code);
                    }
                }
            }
        }
        return ret;
    }

    public static String matchOne(String line, IEnum<T>[] enums) {
        List<String> ret = matchList(line, enums);
        if(CollectionUtils.isNotEmpty(ret) && ret.size() == 1) {
            return ret.get(0);
        }
        return null;
    }

    public static String matchOne(String line, List<String> expectedWords) {
        List<String> words = startIKAnalyzer(line);
        for (String w : words) {
            if (expectedWords.contains(w)) {
                return w;
            }
        }
        return "";
    }

    public static List<String> startIKAnalyzer(String line) {
        if (StringUtils.isBlank(line)) {
            return Lists.newArrayList();
        }
        IKAnalyzer analyzer = new IKAnalyzer();
        analyzer.setUseSmart(true);
        try {
            return printAnalysisResult(analyzer, line);
        } catch (Exception e) {
            log.error("解析{}失败, {}", line, e);
        } finally {
            if (analyzer != null) {
                analyzer.close();
            }
        }
        return null;
    }

    private static List<String> printAnalysisResult(Analyzer analyzer, String keyWord) throws Exception {
        List<String> resultdata = Lists.newArrayList();
        if (keyWord != "" && keyWord != null) {
            TokenStream tokenStream = analyzer.tokenStream("content",
                    new StringReader(keyWord));
            tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                CharTermAttribute charTermAttribute = tokenStream
                        .getAttribute(CharTermAttribute.class);
                log.info(charTermAttribute.toString());
                resultdata.add(charTermAttribute.toString());
            }
        }
        return resultdata;
    }

    public static void main(String[] args) {
        String word = "G--保额算保费";
//        分词.startIKAnalyzer(word);
        log.info("Result: {}", 分词.matchOne(word, Lists.newArrayList("保额算保费")));
    }
}
