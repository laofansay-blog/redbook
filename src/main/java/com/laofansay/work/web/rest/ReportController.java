package com.laofansay.work.web.rest;

import com.laofansay.work.service.ReportService;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/down")
    public ResponseEntity<InputStreamResource> generateReport() throws FileNotFoundException, DRException {
        ByteArrayInputStream bis = reportService.generateReport();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=example.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
    }

    @PostMapping("/sign")
    public ResponseEntity<InputStreamResource> generateSignReport(@RequestParam("file") MultipartFile file)
        throws IOException, DRException {
        ByteArrayInputStream bis = reportService.generateSignReport(file.getInputStream());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=example.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
    }
}
