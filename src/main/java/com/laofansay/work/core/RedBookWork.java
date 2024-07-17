package com.laofansay.work.core;

import cn.hutool.core.thread.ThreadUtil;
import com.laofansay.work.domain.CstAccount;
import com.laofansay.work.domain.CstJob;
import com.laofansay.work.domain.JobResult;
import com.laofansay.work.domain.enumeration.CstStatus;
import com.laofansay.work.domain.enumeration.JobStatus;
import com.laofansay.work.service.CstAccountService;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Cookie;
import com.microsoft.playwright.options.WaitForSelectorState;
import jakarta.annotation.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RedBookWork {

    static String DOMAIN = "https://www.xiaohongshu.com";
    private static final Logger log = LoggerFactory.getLogger(RedBookWork.class);

    @Resource
    CstAccountService cstAccountService;

    public void doSeeCustomer(List<CstJob> list) {
        Browser browser = null;
        Page page = null;
        BrowserContext context = null;
        Playwright playwright = null;
        try {
            playwright = Playwright.create();
            //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            browser = playwright.chromium().connectOverCDP("http://localhost:9222/");

            for (CstJob k : list) {
                List<CstAccount> users = cstAccountService.findByCstId(k.getCustomer().getId());
                if (users.isEmpty()) {
                    continue;
                }
                context = browser.newContext();
                context.addCookies(toArraysCookie(users.get(0).getRbToken()));
                page = context.newPage();
                page.navigate(DOMAIN);
                log.info("title={}", page.title());
                ThreadUtil.sleep(10, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (page != null && !page.isClosed()) {
                    page.close();
                    page = null;
                }
                if (context != null) {
                    context.close();
                    context = null;
                }
                if (browser != null && browser.isConnected()) {
                    browser.close();
                    browser = null;
                }
                if (playwright != null) {
                    playwright.close();
                    playwright = null;
                }
            } catch (PlaywrightException e) {
                System.err.println("Error while closing Playwright resources: " + e.getMessage());
            }
        }
    }

    public List<Cookie> toArraysCookie(String token) {
        // 加载cookie
        List<Cookie> cookies = new ArrayList<>();
        String[] lines = token.split(";");
        for (String line : lines) {
            String[] parts = line.trim().split("=", 2);
            if (parts.length == 2) {
                String name = parts[0];
                String value = parts[1];
                cookies.add(new Cookie(name, value).setDomain(".xiaohongshu.com").setPath("/")); // -1 表示会话 cookie
            }
        }
        return cookies;
    }
}
