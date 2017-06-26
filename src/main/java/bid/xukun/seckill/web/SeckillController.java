package bid.xukun.seckill.web;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bid.xukun.seckill.dto.Exposer;
import bid.xukun.seckill.dto.SeckillExecution;
import bid.xukun.seckill.dto.SeckillResult;
import bid.xukun.seckill.entity.Seckill;
import bid.xukun.seckill.enums.SeckillStateEnum;
import bid.xukun.seckill.exception.RepeatKillException;
import bid.xukun.seckill.exception.SeckillCloseException;
import bid.xukun.seckill.service.SeckillService;

/**
 * 
 * @author XK
 *
 */
@Controller
@RequestMapping("/seckill") // url:/模块/资源/{id}/细分
public class SeckillController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	/**
	 * 获取列表页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> seckillList = seckillService.getSeckillList();
		model.addAttribute("seckillList", seckillList);
		return "list";
	}

	/**
	 * 获取秒杀商品详情页
	 * 
	 * @param seckillId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Integer seckillId, Model model) {
		if (null == seckillId) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getSeckillById(seckillId);
		if (seckill == null) {
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}

	/**
	 * 输出秒杀开启地址json
	 * 
	 * @param seckillId
	 * @return
	 */
	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") int seckillId) {
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}

	/**
	 * 执行秒杀
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	@RequestMapping(value = "/{seckillId}/{md5}/execute", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") int seckillId,
			@CookieValue(value = "userPhone", required = false) String userPhone, @PathVariable("md5") String md5) {
		SeckillResult<SeckillExecution> result;
		if (null == userPhone || "".equals(userPhone)) {
			result = new SeckillResult<>(false, "手机号未注册");
			return result;
		}
		SeckillExecution seckillExecution;
		try {
			seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
		} catch (RepeatKillException e) {
			seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.PEPEAT_KILL);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
			return result;
		} catch (SeckillCloseException e) {
			seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.END);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
			return result;
		} catch (Exception e) {
			seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
			return result;
		}
		return result;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time() {
		Date now = new Date();
		return new SeckillResult<Long>(true, now.getTime());
	}

}
