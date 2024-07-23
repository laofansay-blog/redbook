/*
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2018 Ricardo Mariaca and the Dynamic Reports Contributors
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */
package com.laofansay.work.dynamicreport;

import static net.sf.dynamicreports.report.builder.DynamicReports.bcode;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.exp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Date;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.barcode.Code128BarcodeBuilder;
import net.sf.dynamicreports.report.builder.barcode.Ean128BarcodeBuilder;
import net.sf.dynamicreports.report.builder.component.*;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>ShippingLabelDesign class.</p>
 *
 * @author Ricardo Mariaca
 *
 */
public class ShippingLabelDesign {

    private ShippingLabelData data = new ShippingLabelData();
    private StyleBuilder bold14Style;

    private StyleBuilder textStyle;
    private StyleBuilder centeredStyle;
    private StyleBuilder labelStyle;
    private StyleBuilder cellStyle;

    private static final int cellWidth = 18;
    private static final int cellHeight = 18;

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(String[] args) {
        ShippingLabelDesign design = new ShippingLabelDesign();
        try {
            JasperReportBuilder report = design.build();
            report.show();
        } catch (DRException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>build.</p>
     *
     * @return a {@link net.sf.dynamicreports.jasper.builder.JasperReportBuilder} object.
     * @throws net.sf.dynamicreports.report.exception.DRException if any.
     */
    public JasperReportBuilder build() throws DRException, FileNotFoundException {
        JasperReportBuilder report = report();

        textStyle = stl.style().setFontSize(12).setPadding(2);
        centeredStyle = stl.style(textStyle).setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
        labelStyle = stl.style(textStyle).setHorizontalAlignment(HorizontalAlignment.LEFT).bold();
        cellStyle = stl.style(textStyle).setBorder(stl.pen1Point()).setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);

        ShippingLabel shippingLabel = data.getShippingLabel();

        StyleBuilder textStyle = stl.style().setFontSize(12);
        bold14Style = stl.style(Templates.boldStyle).setFontSize(14);
        StyleBuilder boldCentered30Style = stl
            .style(bold14Style)
            .setFontSize(30)
            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        StyleBuilder boldCentered100Style = stl.style(boldCentered30Style).setFontSize(100);

        Ean128BarcodeBuilder shippingContainerCode = bcode.ean128("100264835710351").setModuleWidth(2.5).setStyle(bold14Style);
        Code128BarcodeBuilder shipToPostalCode = bcode.code128("09820").setModuleWidth(3d).setStyle(bold14Style);
        TextFieldBuilder<Integer> priority = cmp.text(shippingLabel.getPriority()).setStyle(boldCentered100Style);
        TextFieldBuilder<String> pod = cmp.text(shippingLabel.getPod()).setStyle(boldCentered30Style);
        TextFieldBuilder<Date> dateShipped = cmp.text(exp.date(shippingLabel.getDateShipped())).setDataType(type.dateType());
        TextFieldBuilder<String> po = cmp.text(shippingLabel.getPo()).setStyle(boldCentered30Style);

        String signImagePath = getSignImagePath();
        InputStream signImageStream = new FileInputStream(signImagePath);
        ImageBuilder signImage = DynamicReports.cmp
            .image(signImageStream)
            .setFixedDimension(100, 50)
            .setHorizontalAlignment(HorizontalAlignment.RIGHT);

        HorizontalListBuilder address = cmp
            .horizontalList()
            .add(label("Address", 31))
            .newRow()
            .add(textCell("abcd", 31))
            .newRow()
            .add(signImage)
            .add(textCell("", 31))
            .newRow();

        report
            .setTemplate(Templates.reportTemplate)
            .setPageFormat(PageType.A5)
            .setTextStyle(textStyle)
            .pageFooter(signImage) // 在页脚添加签名图片
            .title(
                Templates.createTitleComponent("ShippingLabel"),
                cmp.horizontalList(
                    createCustomerComponent("From", shippingLabel.getFrom()),
                    createCustomerComponent("To", shippingLabel.getTo())
                ),
                cmp.horizontalList(
                    cmp.verticalList(createCellComponent("Priority", priority), createCellComponent("POD", pod)),
                    cmp.verticalList(
                        cmp.horizontalList(
                            createCellComponent("Carrier", cmp.text(shippingLabel.getCarrier())),
                            createCellComponent("Date shipped", dateShipped)
                        ),
                        cmp.horizontalList(
                            createCellComponent("Weight", cmp.text(shippingLabel.getWeight())),
                            createCellComponent("Quantity", cmp.text(shippingLabel.getQuantity()))
                        ),
                        createCellComponent("Ship to postal code", shipToPostalCode)
                    )
                ),
                createCellComponent("PO", po),
                createCellComponent("Serial shipping container", shippingContainerCode)
            );

        return report;
    }

    private ComponentBuilder<?, ?> createCustomerComponent(String label, Customer customer) {
        VerticalListBuilder content = cmp.verticalList(
            cmp.text(customer.getName()),
            cmp.text(customer.getAddress()),
            cmp.text(customer.getCity())
        );
        return createCellComponent(label, content);
    }

    private ComponentBuilder<?, ?> createCellComponent(String label, ComponentBuilder<?, ?> content) {
        VerticalListBuilder cell = cmp.verticalList(
            cmp.text(label).setStyle(bold14Style),
            cmp.horizontalList(cmp.horizontalGap(20), content, cmp.horizontalGap(5))
        );
        cell.setStyle(stl.style(stl.pen2Point()));
        return cell;
    }

    // 模拟根据医生名字获取签名图像路径的方法
    private static String getSignImagePath() {
        String currentDir = System.getProperty("user.dir");
        String imagePath = Paths.get(currentDir, "lib", "signature.png").toString();
        // 替换为实际的获取图像路径的逻辑
        return imagePath;
    }

    private TextFieldBuilder<String> label(String text, int size) {
        return label(text, size, labelStyle);
    }

    private TextFieldBuilder<String> label(String text, int size, StyleBuilder style) {
        TextFieldBuilder<String> label = cmp.text(text).setFixedWidth(cellWidth * size);
        if (style != null) {
            label.setStyle(style);
        }
        return label;
    }

    private HorizontalListBuilder textCell(String text, int size) {
        HorizontalListBuilder list = cmp.horizontalList();
        String cellText = StringUtils.rightPad(text, size);
        cellText = StringUtils.left(cellText, size);
        for (char character : cellText.toCharArray()) {
            TextFieldBuilder<String> cell = cmp
                .text(String.valueOf(character))
                .setStyle(cellStyle)
                .setFixedDimension(cellWidth, cellHeight);
            list.add(cell);
        }
        return list;
    }
}
