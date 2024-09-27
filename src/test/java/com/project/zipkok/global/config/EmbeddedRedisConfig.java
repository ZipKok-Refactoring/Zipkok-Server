package com.project.zipkok.global.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Slf4j
@Profile("test")
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.data.redis.port}")
    private int redisPort;

    private static final String REDIS_SERVER_MAX_MEMORY = "maxmemory 128M";

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() {
        int port = isRedisRunning() ? findAvailablePort() : redisPort;

        if (isArmMac()) {
            redisServer = new RedisServer(getRedisFileForArcMac(), port);
        } else {
            redisServer = RedisServer.builder()
                    .port(port)
                    .setting(REDIS_SERVER_MAX_MEMORY)
                    .build();
        }

        try {
            redisServer.start();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    /**
     * 현재 PC 서버에서 사용 가능한 포트 조회
     */
    public int findAvailablePort() {
        for (int port = 10000; port <= 65535; port++) {
            Process process = executeGrepProcessCommand(port);

            if (!isRunning(process)) {
                return port;
            }
        }

        throw new RuntimeException("사용가능한 포트가 없습니다.");
    }

    /**
     * Embedded Redis가 현재 실행 중인지 확인
     */
    private boolean isRedisRunning() {
        return isRunning(executeGrepProcessCommand(redisPort));
    }

    /**
     * 해당 Port를 사용 중인 프로세스를 확인하는 sh 실행
     */
    private Process executeGrepProcessCommand(int redisPort) {
        // netstat -nat : 시스템 네트워크 연결 상태 확인 명령어
        // grep LISTEN : 'LISTEN'이 포함된 줄 확인 명령어
        // grep %d : 앞서 주어진 출력 내용에서 포트번호가 포함된 줄 확인 명령어
        String command = String.format("netstat -nat | grep LISTEN | grep %d", redisPort);

        //  '/bin/sh'에서 '-c' 옵션과 함께 위에서 만든 command를 실행하는 명령을 배열 형태로 구성
        String[] shell = {"/bin/sh", "-c", command};

        try {
            return Runtime.getRuntime().exec(shell);
        } catch (IOException e) {
            throw new RuntimeException("Embedded Redis 실행 중 오류가 발생했습니다." + e.getMessage());
        }
    }

    /**
     * 해당 Process가 현재 실행 중인지 확인
     */
    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();

        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("Embedded Redis 실행 중 오류가 발생했습니다." + e.getMessage());
        }

        return StringUtils.hasText(pidInfo.toString());
    }

    /**
     * 현재 시스템이 ARM 아키텍처를 사용하는 MAC인지 확인
     * System.getProperty("os.arch") : JVM이 실행되는 시스템 아키텍처 반환
     * System.getProperty("os.name") : 시스템 이름 반환
     */
    private boolean isArmMac() {
        return Objects.equals(System.getProperty("os.arch"), "aarch64")
                && Objects.equals(System.getProperty("os.name"), "Mac OS X");
    }

    /**
     * ARM 아키텍처를 사용하는 Mac에서 실행할 수 있는 Redis 바이너리 파일을 반환
     */
    private File getRedisFileForArcMac() {
        try {
            return new ClassPathResource("binary/redis/redis-server-arm64").getFile();
        } catch (Exception e) {
            throw new RuntimeException("Embedded Redis 실행 중 오류가 발생했습니다." + e.getMessage());
        }
    }
}
