package com.qilin.mall.controller;

import com.qilin.mall.common.api.CommonPage;
import com.qilin.mall.common.api.CommonResult;
import com.qilin.mall.mbg.model.PmsBrand;
import com.qilin.mall.service.PmsBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌管理 Controller
 */
@Controller
@RequestMapping("/brand")
public class PmsBrandController {

    @Autowired
    private PmsBrandService demoService;

    private static final Logger logger = LoggerFactory.getLogger(PmsBrandController.class);

    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsBrand>> getBrandList(){
        return CommonResult.success(demoService.listAllBrand());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand){
        CommonResult commonResult;

        int count = demoService.createBrand(pmsBrand);
        if(count == 1){
            commonResult = CommonResult.success(pmsBrand);
            logger.debug("createBrand success:{}" , pmsBrand);
        }else{
            commonResult = CommonResult.failed("操作失败");
            logger.debug("createBrand failed:{}", pmsBrand);
        }

        return commonResult;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto, BindingResult result){

        CommonResult commonResult;

        int count = demoService.updateBrand(id, pmsBrandDto);
        if(count == 1){
            commonResult = CommonResult.success(pmsBrandDto);
            logger.debug("updateBrand success:{}", pmsBrandDto);
        }else{
            commonResult = CommonResult.failed("操作失败");
            logger.debug("updateBrand failed:{}", pmsBrandDto);
        }

        return commonResult;
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteBrand(@PathVariable("id") Long id){

        int count = demoService.deleteBrand(id);
        if(count == 1){
            logger.debug("deleteBrand success:id={}", id);
            return CommonResult.success(null);
        }else{

            logger.debug("deleteBrand failed :{}", id);
            return CommonResult.failed("操作失败");
        }
    }


    @RequestMapping(value = "/list" , method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize){
        List<PmsBrand> brandList = demoService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsBrand> brand(@PathVariable("id") Long id){
        return CommonResult.success(demoService.getBrand(id));
    }



}
