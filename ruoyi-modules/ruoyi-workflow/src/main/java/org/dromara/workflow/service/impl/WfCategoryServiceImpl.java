package org.dromara.workflow.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.workflow.domain.bo.WfCategoryBo;
import org.dromara.workflow.domain.vo.WfCategoryVo;
import org.dromara.workflow.domain.WfCategory;
import org.dromara.workflow.mapper.WfCategoryMapper;
import org.dromara.workflow.service.IWfCategoryService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 流程分类Service业务层处理
 *
 * @author may
 * @date 2023-06-28
 */
@RequiredArgsConstructor
@Service
public class WfCategoryServiceImpl implements IWfCategoryService {

    private final WfCategoryMapper baseMapper;

    /**
     * 查询流程分类
     */
    @Override
    public WfCategoryVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }


    /**
     * 查询流程分类列表
     */
    @Override
    public List<WfCategoryVo> queryList(WfCategoryBo bo) {
        LambdaQueryWrapper<WfCategory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WfCategory> buildQueryWrapper(WfCategoryBo bo) {
        LambdaQueryWrapper<WfCategory> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCategoryName()), WfCategory::getCategoryName, bo.getCategoryName());
        lqw.eq(StringUtils.isNotBlank(bo.getCategoryCode()), WfCategory::getCategoryCode, bo.getCategoryCode());
        return lqw;
    }

    /**
     * 新增流程分类
     */
    @Override
    public Boolean insertByBo(WfCategoryBo bo) {
        WfCategory add = MapstructUtils.convert(bo, WfCategory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改流程分类
     */
    @Override
    public Boolean updateByBo(WfCategoryBo bo) {
        WfCategory update = MapstructUtils.convert(bo, WfCategory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(WfCategory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除流程分类
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
