package com.laofansay.work.service;

import com.laofansay.work.dynamicreport.subtotal.CustomTextSubtotalReport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    public ByteArrayInputStream generateReport() throws FileNotFoundException, DRException {
        CustomTextSubtotalReport customTextSubtotalReport = new CustomTextSubtotalReport();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        customTextSubtotalReport.build(null).toPdf(baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return bais;
    }

    public ByteArrayInputStream generateSignReport(InputStream signImageStream) throws FileNotFoundException, DRException {
        CustomTextSubtotalReport customTextSubtotalReport = new CustomTextSubtotalReport();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        customTextSubtotalReport.build(signImageStream).toPdf(baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return bais;
    }
}
