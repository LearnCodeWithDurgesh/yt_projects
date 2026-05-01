package com.substring.sse.sseexample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/status")
public class SeeStatusController {





    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();


    //    subscribe


    @GetMapping("/subscribe/{jobId}")
    public SseEmitter subscribe(@PathVariable String jobId) {

        System.out.println("subscribing to jobid "+jobId);

        var emitter = new SseEmitter(0L);
        emitters.put(jobId, emitter);
        emitter.onCompletion(() -> {
            System.out.println("Connection closed "+jobId);
            emitters.remove(jobId);
            System.out.println("emitters size "+emitters.size());
        });
        emitter.onTimeout(() -> emitters.remove(jobId));

        return emitter;


    }


    // start the job
    @PostMapping("/start/{jobId}")
    public Map<String,String> startJob(@PathVariable String jobId) {

        new Thread(() -> processJob(jobId)).start();

        return Map.of("message", "Job started");
    }

    private void processJob(String jobId) {

        try {


            sendUpdate(jobId, "PENDING....");
            Thread.sleep(2000);
            sendUpdate(jobId, "UPLOADING....");
            Thread.sleep(2000);
            sendUpdate(jobId,"PROCESSING 25%....");
            Thread.sleep(2000);
            sendUpdate(jobId,"PROCESSING 50%....");
            Thread.sleep(2000);
            sendUpdate(jobId,"PROCESSING 75%...");
            Thread.sleep(2000);
            sendUpdate(jobId,"PROCESSING 100%...");
            Thread.sleep(2000);
            sendUpdate(jobId, "COMPLETED");
            emitters.get(jobId).complete();


        } catch (Exception e) {
            e.printStackTrace();
            sendUpdate(jobId,"FAILED");
        }

    }

    private void sendUpdate(String jobId, String status) {
        SseEmitter sseEmitter = emitters.get(jobId);
        System.out.println(sseEmitter);







        if (sseEmitter != null) {
            try {
                sseEmitter.send(SseEmitter.event().data(status));
            } catch (IOException e) {

                sseEmitter.complete();
                emitters.remove(jobId);
                e.printStackTrace();
            }
        }else{
            throw new RuntimeException("No emitter found for job id: " + jobId);
        }

    }



}
