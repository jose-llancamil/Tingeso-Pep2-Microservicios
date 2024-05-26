package com.autofix.msrepairs.clients;

import com.autofix.msrepairs.configurations.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "ms-repair-list", path = "/repair-list", configuration = FeignClientConfig.class)
public interface RepairListFeignClient {

    @GetMapping("/price")
    BigDecimal getRepairPrice(@RequestParam("repairType") String repairType, @RequestParam("engineType") String engineType);
}