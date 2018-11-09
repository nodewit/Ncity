package com.ncity.app.action;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ncity.app.activeMQ.PromoteActProducer;
import com.ncity.app.bean.ResponseBean;
import com.ncity.app.entity.ChainEntity;
import com.ncity.app.entity.DiamondLogEntity;
import com.ncity.app.service.BlockchainService;
import com.ncity.app.uitls.Constants;
import com.ncity.app.uitls.RemoteExecuteCommand;

import io.ipfs.api.IPFS;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 区块链服务接口
 * 
 * @author 艾克 2018年10月19日 09点20分
 */
@Api(value = "区块链服务接口", tags = { "区块链服务接口" })
@Controller
@RequestMapping("/blockchain")
public class BlockchainAction {

	private Logger log = Logger.getLogger(BlockchainAction.class);
	@Autowired
	private BlockchainService blockchainService;
	@Autowired
	private PromoteActProducer promoteActProducer;
	@Value("${uploadDirPath}")
	private String uploadDirPath;
	@Value("${ipfsPath}")
	private String ipfsPath;
	
	/**
	 * 灵钻查询
	 *
	 * @param reuqset
	 * @return
	 */
	@ApiOperation(value = "灵钻查询接口", notes = "灵钻查询接口")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "用户UUID", required = true, dataType = "String", paramType = "query", name = "uuid") })
	@RequestMapping(value = "/diamond", method = { RequestMethod.POST })
	@ApiResponses({ @ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"), })
	@ResponseBody
	public ResponseBean diamond(String uuid,HttpServletRequest reuqset) {
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("访问成功");
		try {
			double sum = blockchainService.getUserDiamond(uuid);
			result.setData(sum);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 灵钻日志列表
	 * @param pageNumber
	 * @param pageSize
	 * @param uuid
	 * @param createTime
	 * @return
	 */
	@ApiOperation(value = "灵钻日志列表接口", notes = "灵钻日志列表接口")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "页数", required = true, dataType = "int", paramType = "query", name = "pageNumber"),
			@ApiImplicitParam(value = "每页数量", required = true, dataType = "int", paramType = "query", name = "pageSize"),
			@ApiImplicitParam(value = "uuid", required = true, dataType = "String", paramType = "query", name = "uuid"),
			@ApiImplicitParam(value = "receive", required = true, dataType = "int", paramType = "query", name = "receive"),//1：已领取 0：未领取
			@ApiImplicitParam(value = "创建时间", required = false, dataType = "long", paramType = "query", name = "createTime") })

	@ApiResponses({ @ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"), })
	@RequestMapping(value = "/queryDiamondLogByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseBean queryDiamondLogByPage(int pageNumber, int pageSize, String uuid, String receive, Long createTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			if (uuid != null) {
				params.put("uuid", uuid);
			}
			if (createTime != null) {
				params.put("createTime", createTime);
			}
			if (receive != null) {
				params.put("receive", receive);
			}
			List<DiamondLogEntity> list = blockchainService.queryDiamondLogByPage(pageNumber, pageSize, params);
			if (list == null) {
				list = new ArrayList<DiamondLogEntity>();
			}
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 更新灵钻
	 * @param diamondLogId
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = "更新灵钻接口", notes = "更新灵钻接口")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "uuid", required = false, dataType = "String", paramType = "query", name = "uuid"),
			@ApiImplicitParam(value = "灵钻ID", required = false, dataType = "int", paramType = "query", name = "diamondLogId") })
	@ApiResponses({ @ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"), })
	@RequestMapping(value = "/updateDiamondLog", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseBean updateDiamondLog(int diamondLogId, String uuid) {
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("更新成功");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("diamondLogId", diamondLogId);
			map.put("uuid", uuid);
			JSONObject json = JSONObject.fromObject(map);
			blockchainService.lockingDiamondLog(uuid, diamondLogId);
			promoteActProducer.send(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 保存图片或音频
	 * @param uuid
	 * @param type
	 * @param typeId
	 * @param description
	 * @return
	 */
	@ApiOperation(value = "保存图片或音频接口", notes = "保存图片或音频接口")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "uuid", required = true, dataType = "String", paramType = "query", name = "uuid"),
			@ApiImplicitParam(value = "公开或私密", required = true, dataType = "int", paramType = "query", name = "type"),
			@ApiImplicitParam(value = "常用类型表ID", required = true, dataType = "long", paramType = "query", name = "typeId"),
			@ApiImplicitParam(value = "描述", required = true, dataType = "String", paramType = "body", name = "description")
	})
	@ApiResponses({ @ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"), })
	@RequestMapping(value = "/saveImageAndVoice", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseBean saveImageAndVoice(String uuid, int type, long typeId, String description, HttpServletRequest request) {
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)(request);
		Iterator<String> files = mRequest.getFileNames();
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("保存成功");
		Map<String, Object> assetData = new TreeMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			MultipartFile file = null;
			BufferedOutputStream stream = null;
			while (files.hasNext()) {
				Map<String, Object> params = new HashMap<String, Object>();
				file = mRequest.getFile(files.next());;
				if (!file.isEmpty()) {
					try {
						String uploadFilePath = file.getOriginalFilename();
						// 截取上传文件的文件名
						String uploadFileName = uploadFilePath.substring(uploadFilePath.lastIndexOf('\\') + 1,
								uploadFilePath.indexOf('.'));
						params.put("uploadFileName", uploadFileName);
						// 截取上传文件的后缀
						String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf(".")+1);
						params.put("type", uploadFileSuffix);
						params.put("size", file.getSize());
						byte[] bytes = file.getBytes();
						String uid = UUID.randomUUID().toString().replace("-","");
						params.put("uid", uid);
						//文件夹不存在就创建
						File f = new File(uploadDirPath);
						if(!f.isDirectory()){
							f.mkdir();
						}
						//读取文件
						stream = new BufferedOutputStream(new FileOutputStream(new File(uploadDirPath+uid+"."+uploadFileSuffix)));
						//写入文件
						stream.write(bytes);
						//通过客户端上传文件到ipfs并同步
						RemoteExecuteCommand r = new RemoteExecuteCommand("192.168.0.18", "root", "123456");
						String temp = r.executeSuccess("ipfs-cluster-ctl add "+uploadDirPath+uid+"."+uploadFileSuffix);
						if(temp.indexOf("added") > -1) {
							String[] t = temp.split(" ");
							params.put("ipfsPath", t[1]);
						}else{
							params.put("ipfsPath", "");
						}
						list.add(params);
						File f1 = new File(uploadDirPath+uid+"."+uploadFileSuffix);
						f1.delete();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							if (stream != null) {
								stream.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {
					log.info("上传文件为空");
				}
			}
			assetData.put("description", description);
			assetData.put("files", list);
			assetData.put("date", new Date().getTime());
			blockchainService.saveImageAndVoice(uuid, type, typeId, assetData);
			log.info("文件接受成功了");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 时光机/一诺千金/发现的列表
	 * @param pageNumber
	 * @param pageSize
	 * @param uuid
	 * @param type
	 * @param typeId
	 * @param createTime
	 * @return
	 */
	@ApiOperation(value = "时光机/一诺千金/发现的列表接口", notes = "时光机/一诺千金/发现的列表接口")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "页数", required = true, dataType = "int", paramType = "query", name = "pageNumber"),
			@ApiImplicitParam(value = "每页数量", required = true, dataType = "int", paramType = "query", name = "pageSize"),
			@ApiImplicitParam(value = "uuid", required = true, dataType = "String", paramType = "query", name = "uuid"),
			@ApiImplicitParam(value = "公开/私密", required = false, dataType = "String", paramType = "query", name = "type"),
			@ApiImplicitParam(value = "时光机/一诺千金", required = false, dataType = "String", paramType = "query", name = "typeId"),
			@ApiImplicitParam(value = "创建时间", required = false, dataType = "long", paramType = "query", name = "createTime") })
	@ApiResponses({ @ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"), })
	@RequestMapping(value = "/queryListChainByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseBean queryListChainByPage(int pageNumber, int pageSize, String uuid, String type, String typeId, Long createTime, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			if (uuid != null) {
				params.put("uuid", uuid);
			}
			if (typeId != null) {
				params.put("typeId", typeId);
			}
			if (type != null) {
				params.put("type", type);
			}
			if (createTime != null) {
				params.put("createTime", createTime);
			}
			List<ChainEntity> list = blockchainService.queryListChain(pageNumber, pageSize, params, request);
			if (list == null) {
				list = new ArrayList<ChainEntity>();
			}
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 时光机/一诺千金详情
	 * @param uuid 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "时光机/一诺千金详情接口", notes = "时光机/一诺千金详情接口")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "用户UUID", required = true, dataType = "String", paramType = "query", name = "uuid"),
			@ApiImplicitParam(value = "实体ID", required = true, dataType = "long", paramType = "query", name = "id")
	})
	@RequestMapping(value = "/queryChainById", method = { RequestMethod.GET })
	@ApiResponses({ @ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"), })
	@ResponseBody
	public ResponseBean queryChainById(String uuid, long id, HttpServletRequest request) {
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("访问成功");
		try {
			ChainEntity chain = blockchainService.queryChainById(id, uuid, request);
			result.setData(chain);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 微信小程序上传图片或音频
	 * @param uuid
	 * @param type
	 * @param typeId
	 * @param description
	 * @return
	 */
	@ApiOperation(value = "微信小程序上传图片或音频接口", notes = "微信小程序上传图片或音频接口")
	@ApiResponses({ @ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"), })
	@RequestMapping(value = "/uploadWxImageAndVoice", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseBean uploadWxImageAndVoice(HttpServletRequest request) {
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)(request);
		Iterator<String> files = mRequest.getFileNames();
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("保存成功");
		List list = new ArrayList();
		try {
			MultipartFile file = null;
			BufferedOutputStream stream = null;
			while (files.hasNext()) {
				Map<String, Object> params = new HashMap<String, Object>();
				file = mRequest.getFile(files.next());;
				if (!file.isEmpty()) {
					try {
						String uploadFilePath = file.getOriginalFilename();
						// 截取上传文件的文件名
						String uploadFileName = uploadFilePath.substring(uploadFilePath.lastIndexOf('\\') + 1,
								uploadFilePath.indexOf('.'));
						params.put("uploadFileName", uploadFileName);
						// 截取上传文件的后缀
						String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf(".")+1);
						params.put("type", uploadFileSuffix);
						params.put("size", file.getSize());
						byte[] bytes = file.getBytes();
						String uid = UUID.randomUUID().toString().replace("-","");
						params.put("uid", uid);
						//文件夹不存在就创建
						File f = new File(uploadDirPath);
						if(!f.isDirectory()){
							f.mkdir();
						}
						//读取文件
						stream = new BufferedOutputStream(new FileOutputStream(new File(uploadDirPath+uid+"."+uploadFileSuffix)));
						//写入文件
						stream.write(bytes);
						//上传文件到ipfs
						RemoteExecuteCommand r = new RemoteExecuteCommand("192.168.0.18", "root", "123456");
						String temp = r.executeSuccess("ipfs-cluster-ctl add "+uploadDirPath+uid+"."+uploadFileSuffix);
						if(temp.indexOf("added") > -1) {
							String[] t = temp.split(" ");
							params.put("ipfsPath", t[1]);
						}else{
							params.put("ipfsPath", "");
						}
						File f1 = new File(uploadDirPath+uid+"."+uploadFileSuffix);
						f1.delete();
						list.add(params);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							if (stream != null) {
								stream.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {
					log.info("上传文件为空");
				}
			}
			result.setData(list);
			log.info("微信文件接受成功了");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 微信小程序保存图片或音频
	 * @param uuid
	 * @param type
	 * @param typeId
	 * @param description
	 * @return
	 */
	@ApiOperation(value = "微信小程序保存图片或音频接口", notes = "微信小程序保存图片或音频接口")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "uuid", required = true, dataType = "String", paramType = "body", name = "uuid"),
			@ApiImplicitParam(value = "公开或私密", required = true, dataType = "int", paramType = "body", name = "types"),
			@ApiImplicitParam(value = "常用类型表ID", required = true, dataType = "long", paramType = "body", name = "typeId"),
			@ApiImplicitParam(value = "描述", required = true, dataType = "String", paramType = "body", name = "description"),
			@ApiImplicitParam(value = "文件集合", required = true, dataType = "String", paramType = "body", name = "files")
	})
	@ApiResponses({ @ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"), })
	@RequestMapping(value = "/saveWxImageAndVoice", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseBean saveWxImageAndVoice(@RequestBody Map<String, Object> map) {
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("保存成功");
		Map<String, Object> assetData = new TreeMap<String, Object>();
		try {
			assetData.put("description", map.get("description").toString());
			assetData.put("files", map.get("files"));
			assetData.put("date", new Date().getTime());
			blockchainService.saveImageAndVoice(map.get("uuid").toString(), Integer.parseInt(map.get("types").toString()), Long.parseLong(map.get("typeId").toString()), 
					assetData);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 证书列表
	 * @param pageNumber
	 * @param pageSize
	 * @param uuid
	 * @param createTime
	 * @return
	 */
	@ApiOperation(value = "证书列表接口", notes = "证书列表接口")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "页数", required = true, dataType = "int", paramType = "query", name = "pageNumber"),
			@ApiImplicitParam(value = "每页数量", required = true, dataType = "int", paramType = "query", name = "pageSize"),
			@ApiImplicitParam(value = "uuid", required = true, dataType = "String", paramType = "query", name = "uuid"),
			@ApiImplicitParam(value = "创建时间", required = false, dataType = "long", paramType = "query", name = "createTime") })
	@ApiResponses({ @ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"), })
	@RequestMapping(value = "/queryListChainProveByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseBean queryListChainProveByPage(int pageNumber, int pageSize, String uuid, Long createTime, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			if (uuid != null) {
				params.put("uuid", uuid);
			}
			if (createTime != null) {
				params.put("createTime", createTime);
			}
			List<ChainEntity> list = blockchainService.queryListChainProve(pageNumber, pageSize, params, request);
			if (list == null) {
				list = new ArrayList<ChainEntity>();
			}
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
}
