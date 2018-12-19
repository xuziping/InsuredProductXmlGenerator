package com.xuzp.insuredxmltool;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author za-xuzhiping
 * @Date 2018/12/19
 * @Time 18:45
 */
public class IKAnalyzerSupplyProduct {

    public static String startIKAnalyzer(String line) throws IOException{
        IKAnalyzer analyzer = new IKAnalyzer();
        // 使用智能分词
        analyzer.setUseSmart(true);
        // 打印分词结果
        try {
            return printAnalysisResult(analyzer, line);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(analyzer!=null){
                analyzer.close();
            }
        }
        return null;
    }
    private static String printAnalysisResult(Analyzer analyzer, String keyWord)
            throws Exception {
        String resultdata="";
        String infoData="";
        if(keyWord!=""&&keyWord!=null){
            TokenStream tokenStream = analyzer.tokenStream("content",
                    new StringReader(keyWord));
            tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                CharTermAttribute charTermAttribute = tokenStream
                        .getAttribute(CharTermAttribute.class);
            }
            if(infoData!=""&&infoData!=null){
                resultdata=resultdata+infoData.trim()+"\r\n";
            }else{
                resultdata="";
            }
        }
        return resultdata;
    }

    public static void main(String[] args) {
        String word ="8月17日，“雄鹰突击-2018”中国和白俄罗斯特种部队联合训练，在北部战区陆军某综合训练基地完成综合演练并举行结训仪式。北部战区副司令员兼北部战区陆军司令员王印芳、白俄罗斯武装力量总参谋长兼国防部第一副部长别洛科涅夫出席结训仪式。" +
                "记者在综合演练现场看到，联合突击营救行动展开后，双方特战队员混编成多个战斗小组，各小组指挥员通过眼神和手语快速交流现场侦察情况，分配任务、搜索前行、交替掩护等一系列动作顺畅默契，判定目标后迅即展开行动。演练中，中白特战队员密切协同，出色完成立体封控、分区搜歼等一系列行动，赢得两军观摩团阵阵掌声。";
        String result=null;
        try {
            result = IKAnalyzerSupplyProduct.startIKAnalyzer(word);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("result"+result);
    }
}
