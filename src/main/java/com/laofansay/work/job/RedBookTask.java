package com.laofansay.work.job;

import com.laofansay.work.core.RedBookWork;
import com.laofansay.work.domain.CstJob;
import com.laofansay.work.domain.JobResult;
import com.laofansay.work.service.CstJobService;
import com.laofansay.work.service.JobResultService;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
public class RedBookTask {

    private static final Logger log = LoggerFactory.getLogger(RedBookTask.class);

    @Resource
    CstJobService cstJobService;

    @Resource
    RedBookWork redBookWork;

    @Scheduled(fixedDelay = 300000L)
    public void doMaster() {
        log.info("------query Google keyword  task readying go -----");
        try {
            List<CstJob> content = cstJobService.findWaitList(100);
            redBookWork.doSeeCustomer(content);
        } catch (Exception exception) {
            log.error(exception.toString());
        }
    }
}
