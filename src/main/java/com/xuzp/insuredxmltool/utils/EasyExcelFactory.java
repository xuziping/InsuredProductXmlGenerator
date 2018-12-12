package com.xuzp.insuredxmltool.utils;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 *
 * @author za-xuzhiping
 * @Date 2018/12/11
 * @Time 18:33
 */
public class EasyExcelFactory {

    public static ExcelReader getExcelReader(InputStream in, Object customContent,
                                             AnalysisEventListener<?> eventListener) throws EmptyFileException, IOException, InvalidFormatException {
        // 如果输入流不支持mark/reset，需要对其进行包裹
        if (!in.markSupported()) {
            in = new PushbackInputStream(in, 8);
        }

        // 确保至少有一些数据
        byte[] header8 = IOUtils.peekFirst8Bytes(in);
        ExcelTypeEnum excelTypeEnum = null;
        if (NPOIFSFileSystem.hasPOIFSHeader(header8)) {
            excelTypeEnum = ExcelTypeEnum.XLS;
        }
        if (DocumentFactoryHelper.hasOOXMLHeader(in)) {
            excelTypeEnum = ExcelTypeEnum.XLSX;
        }
        if (excelTypeEnum != null) {
            return new ExcelReader(in, excelTypeEnum, customContent, eventListener);
        }
        throw new InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");

    }

    public static ExcelReader getExcelReader(InputStream in, Object customContent,
                                             AnalysisEventListener<?> eventListener, boolean trim)
            throws EmptyFileException, IOException, InvalidFormatException {
        // 如果输入流不支持mark/reset，需要对其进行包裹
        if (!in.markSupported()) {
            in = new PushbackInputStream(in, 8);
        }

        // 确保至少有一些数据
        byte[] header8 = IOUtils.peekFirst8Bytes(in);
        ExcelTypeEnum excelTypeEnum = null;
        if (NPOIFSFileSystem.hasPOIFSHeader(header8)) {
            excelTypeEnum = ExcelTypeEnum.XLS;
        }
        if (DocumentFactoryHelper.hasOOXMLHeader(in)) {
            excelTypeEnum = ExcelTypeEnum.XLSX;
        }
        if (excelTypeEnum != null) {
            return new ExcelReader(in, excelTypeEnum, customContent, eventListener, trim);
        }
        throw new InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
    }
}