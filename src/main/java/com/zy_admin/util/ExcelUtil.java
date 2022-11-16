package com.zy_admin.util;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class ExcelUtil {
    //设置excel内容格式
    public static HorizontalCellStyleStrategy getContentStyle() {
        //内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置样式
        //设置底边框;
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        contentWriteCellStyle.setBottomBorderColor((short) 0);
        //设置左边框;
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        contentWriteCellStyle.setLeftBorderColor((short) 0);
        //设置右边框;
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        contentWriteCellStyle.setRightBorderColor((short) 0);
        //设置顶边框;
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        contentWriteCellStyle.setTopBorderColor((short) 0);
        //设置自动换行;
        contentWriteCellStyle.setWrapped(true);
        // 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(contentWriteCellStyle, contentWriteCellStyle);

        return horizontalCellStyleStrategy;
    }
}
