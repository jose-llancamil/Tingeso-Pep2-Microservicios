package com.autofix.msrepairs.clients;

import com.autofix.msrepairs.configurations.FeignClientConfig;
import com.autofix.msrepairs.requests.PriceListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "ms-repair-list", path = "/repair-list", configuration = FeignClientConfig.class)
public interface RepairListFeignClient {

    @GetMapping
    List<PriceListDTO> getAllRepairs();
}