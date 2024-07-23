package com.laofansay.work.dynamicreport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.ImageBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.Styles;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class SignatureReport {

    public static void main(String[] args) {
        try {
            // 创建报表样式
            StyleBuilder boldStyle = Styles.style().bold();
            StyleBuilder centeredStyle = Styles.style().setHorizontalAlignment(HorizontalAlignment.CENTER);
            StyleBuilder titleStyle = Styles.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER).setFontSize(15);

            // 创建列
            TextColumnBuilder<String> itemColumn = Columns.column("Item", "item", DynamicReports.type.stringType());
            TextColumnBuilder<Integer> quantityColumn = Columns.column("Quantity", "quantity", DynamicReports.type.integerType());

            // 创建报表组件
            ComponentBuilder<?, ?> title = DynamicReports.cmp.text("Signature Report").setStyle(titleStyle);

            String signImagePath = getSignImagePath();
            InputStream signImageStream = new FileInputStream(signImagePath);
            ImageBuilder signImage = DynamicReports.cmp
                .image(signImageStream)
                .setFixedDimension(100, 50)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);

            // 加载签名图片
            //            InputStream signImageStream = SignatureReport.class.getResourceAsStream("/path/to/signature.png");
            //            ImageBuilder signImage = DynamicReports.cmp.image(signImageStream).setFixedDimension(100, 50).setHorizontalAlignment(HorizontalAlignment.RIGHT);

            // 生成报表
            DynamicReports.report()
                .setColumnTitleStyle(boldStyle)
                .highlightDetailEvenRows()
                .columns(itemColumn, quantityColumn)
                .title(title)
                .pageFooter(signImage) // 在页脚添加签名图片
                .setDataSource(createDataSource())
                .show();
        } catch (DRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // 模拟根据医生名字获取签名图像路径的方法
    private static String getSignImagePath() {
        String currentDir = System.getProperty("user.dir");
        String imagePath = Paths.get(currentDir, "lib", "signature.png").toString();
        // 替换为实际的获取图像路径的逻辑
        return imagePath;
    }

    private static JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("item", "quantity");
        dataSource.add("Item 1", 5);
        dataSource.add("Item 2", 7);
        return dataSource;
    }
}
