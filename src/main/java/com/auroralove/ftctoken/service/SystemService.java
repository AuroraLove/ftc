package com.auroralove.ftctoken.service;

import com.auroralove.ftctoken.entity.TeamEntity;
import com.auroralove.ftctoken.filter.Ufilter;
import com.auroralove.ftctoken.mapper.SystemMapper;
import com.auroralove.ftctoken.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * 系统参数服务
 *
 * @author zyu
 * @date 2019/1/24
 */
@Service
public class SystemService {

    @Autowired
    private SystemMapper systemMapper;

    @Value("${picture.url}")
    private String pictureUrl;

    /**
     * 获取用户数据中心
     * @return
     */
    public DataCenterModel getDataCenter() {
        DataCenterModel dataCenterModel = new DataCenterModel();
        dataCenterModel = systemMapper.getDataCenter();
        //取总可交易,系统总释放金额+系统总奖励金额+系统隐藏可交易金额+系统交易买卖单可用余额
        Double trableAmount = dataCenterModel.getTotalReleaseAmount() + dataCenterModel.getTotalReward()
                + dataCenterModel.getSystemTotalAmount() + dataCenterModel.getTotalDealAmount();
        trableAmount = trableAmount > 0 ? trableAmount:0;
        //未释放 = 总充值 - 总释放
        Double releaseAmount = dataCenterModel.getTotalRegistAmount() - dataCenterModel.getTotalReleaseAmount();
        dataCenterModel.setTotalTrableAmount(trableAmount);
        dataCenterModel.setTotalUnReleaseAmount(releaseAmount);
        return dataCenterModel;
    }

    /**
     * 更新帮助中心
     * @return
     */
    @Transactional
    public int updateHelp(HelpModel helpModel,HttpServletRequest request) throws Exception {
        int i = systemMapper.updateHelp(helpModel);
        //更新图片
        if (helpModel.getPictureModels() != null && helpModel.getPictureModels().size() > 0){
            for (PictureModel pictureModel:helpModel.getPictureModels()) {
                i = systemMapper.updateHelpPicture(pictureModel);
            }
        }
        return i;
    }

    @Transactional
    public int deleteHelp(HelpModel helpModel) {
        int i = systemMapper.deleteHelp(helpModel);
        //删除帮助中心图片
        int n = systemMapper.deletePicture(helpModel);
        return i;
    }

    public List<SystemLevelModel> getSystemLevel() {
        List<SystemLevelModel> systemLevelModels = systemMapper.getSystemLevel();
        return systemLevelModels;
    }

    public int updateSystemLevel(SystemLevelModel systemLevelModel) {
        int i = systemMapper.updateSystemLevel(systemLevelModel);
        return  i;
    }

    /**
     * 存储图片
     * @param picture
     * @param request
     * @return
     * @throws Exception
     */
    private String savePicture(MultipartFile picture, HttpServletRequest request) throws Exception{
        String fileName=picture.getOriginalFilename();
        // 设置存放图片文件的路径
        File file=new File(pictureUrl);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 转存文件到指定的路径
        picture.transferTo(new File(pictureUrl + fileName));
        return  fileName;
    }

    public String uploadPicture(PictureModel pictureModel, HttpServletRequest request) throws Exception {
        if (pictureModel.getPicture()!=null){
            String picture = savePicture(pictureModel.getPicture(), request);
            return picture;
        }
        return "";
    }

}
