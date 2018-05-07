package com.example.demo.service;

import com.example.demo.mybatis.domain.TClick;
import com.example.demo.mybatis.domain.TClickQuery;
import com.example.demo.mybatis.service.TClickService;
import com.example.demo.swagger.model.ClickSearch;
import com.example.demo.swagger.model.ClickVo;
import com.example.demo.util.TransferUtil;
import com.example.demo.valid.ClickValid;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ClickManage extends BaseManage<Integer>{

    @Autowired
    TClickService<TClick, TClickQuery, Long> service;

    @Override
    public TClickService<TClick, TClickQuery, Long> getService() {
        return service;
    }

    @Autowired
    ClickValid clickValid;

    /**
     * 分页构建query
     * @param clickSearch
     * @return
     */
    private TClickQuery buildQuery(ClickSearch clickSearch){
        TClickQuery query = new TClickQuery();
        TClickQuery.Criteria criteria = query.createCriteria();
        if(clickSearch != null){
            if(clickSearch.getStart() == null){
                clickSearch.setStart(0);
            }
            if(clickSearch.getLength() == null){
                clickSearch.setLength(10);
            }
            if(clickSearch.getId() != null){
                criteria.andIdEqualTo(clickSearch.getId());
            }
            if(clickSearch.getVideoId() != null){
                criteria.andVideoIdEqualTo(clickSearch.getVideoId());
            }
        }
        return query;
    }

    /**
     * 分页列表
     * @param clickSearch
     * @return
     */
    public List<TClick> getByPage(ClickSearch clickSearch){
        TClickQuery query = buildQuery(clickSearch);
        query.setOrderByClause("id desc");
        return getService().findBy(new RowBounds(clickSearch.getStart(), clickSearch.getLength()), query);
    }

    /**
     * 分页总数量
     * @param clickSearch
     * @return
     */
    public int getPageCount(ClickSearch clickSearch) {
        return getService().countByExample(buildQuery(clickSearch));
    }

    /**
     * 新增
     * @param clickVo
     * @return
     */
    public boolean save(ClickVo clickVo){
        // 校验入参
        clickValid.saveValid(clickVo);
        // 对象转化
        TClick dist = new TClick();
        TransferUtil.transfer(dist, clickVo);
        // 持久化
        int i = create(dist);
        // 返回结果
        if(i == 1){
            return true;
        }
        return false;
    }

    /**
     * 修改
     * @param clickVo
     * @return
     */
    public boolean update(ClickVo clickVo){
        // 校验入参
        clickValid.updateValid(clickVo);
        // 对象转化
        TClick dist = new TClick();
        TransferUtil.transfer(dist, clickVo);
        // 持久化
        int i = modify(dist);
        // 返回结果
        if(i == 1){
            return true;
        }
        return false;
    }

    /**
     * 根据视频ID 修改点击率
     * 如果当前视频ID不存在点击率,则添加一条视频ID关联的点击率
     * @param videoId
     * @return
     */
    public boolean update(Integer videoId){
        int i = getService().updateBySQL("UPDATE t_click SET chick_rate = chick_rate+1 WHERE video_id = "+videoId);
        if(i == 1){
            return true;
        }else {
            TClick tc = new TClick();
            tc.setVideoId(videoId);
            tc.setChickRate(1);
            tc.setModifyTime(new Date());
            int j =getService().save(tc);
            if(j == 1)
                return true;
            else
                return false;
        }
    }

}
