package com.sj.privilegemanagement.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.sj.common.dao.BaseDao;
import com.sj.privilegemanagement.entity.Menu;

public interface MenuDao extends BaseDao<Menu> {

	public List<Menu> findTreeByUserId(Long string);

	public List<Menu> findByUserIdAndFolId(Long id, long l);

	public List<String> findLinkUrlByUserId(long id);

	public boolean findByLinkUrl(String linkUrl);

	public List<Map<String, Object>> findall();

	public List<Map<String, Object>> findmlist(BigInteger bigint);

	public List<Map<String, Object>> getTopMenu();

	public void editemty(String menuname, String mid);

	public void editmeny(String id, String menuname, String mid);

}
