package exec;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import DBUtil.JdbcHelper;

public class CreateParameterInCLGR {

	protected static Logger logger = LogManager.getLogger(CreateParameterInCLGR.class);
	private static JdbcHelper jdbc = new JdbcHelper();

	public static void main(String[] args) {
		if(createToolParameter()){
			logger.info("####################创建刀具参数成功####################");
		}else{
			logger.info("####################创建刀具参数失败####################");
		}
		
		if(createListParameter()){
			logger.info("####################创建刀具列表参数成功####################");
		}else{
			logger.info("####################创建刀具列表参数失败####################");
		}
	}

	@SuppressWarnings({ "static-access", "rawtypes" })
	public static boolean createToolParameter() {
		String sql_query_toolClass = "SELECT TOOLCLASSID, COMPORTOOL FROM TDM_TOOLCLASS";
		List toolClassList = null;
		try {
			toolClassList = jdbc.query(sql_query_toolClass);
		} catch (SQLException e) {
			logger.error("查询刀具类别出错！");
			logger.error(e.getMessage(), e);
			return false;
		}

		String sql_item1 = "INSERT INTO  TDM_TOOLCLASSFIELDS      ( TIMESTAMP ,    TOOLCLASSID ,    TOOLCLASSFIELDSPOS ,    INDEXID ,    NAME ,    NAME01 ,    NAME2 ,    NAME201 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    FIELDTYPEEX ,    POS ,    COMPORTOOL )    VALUES   ( 1111111111 ,    ? ,    100 ,    N'1' ,    N'备注1' ,    N'备注1' ,    N'杭汽-刀具备注1' ,    N'杭汽-刀具备注1' ,    N'CHAR(200)' ,    N'CLGR' ,    N'-' ,    0 ,    0 ,    1 ,    1 )";
		String sql_item2 = "INSERT INTO  TDM_TOOLCLASSFIELDS      ( TIMESTAMP ,    TOOLCLASSID ,    TOOLCLASSFIELDSPOS ,    INDEXID ,    NAME ,    NAME01 ,    NAME2 ,    NAME201 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    FIELDTYPEEX ,    POS ,    COMPORTOOL )    VALUES   ( 1111111111 ,    ? ,    101 ,    N'1' ,    N'备注2' ,    N'备注2' ,    N'杭汽-刀具备注2' ,    N'杭汽-刀具备注2' ,    N'CHAR(200)' ,    N'CLGR' ,    N'-' ,    0 ,    0 ,    2 ,    1 )   ";
		String sql_tool1 = "INSERT INTO  TDM_TOOLCLASSFIELDS      ( TIMESTAMP ,    TOOLCLASSID ,    TOOLCLASSFIELDSPOS ,    INDEXID ,    NAME ,    NAME01 ,    NAME2 ,    NAME201 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    FIELDTYPEEX ,    POS ,    COMPORTOOL )    VALUES   ( 1111111111 ,    ? ,    102 ,    N'1' ,    N'刀补' ,    N'刀补' ,    N'杭汽-刀补' ,    N'杭汽-刀补' ,    N'CHAR(200)' ,    N'CLGR' ,    N'-' ,    0 ,    0 ,    3 ,    2 )   ";
		String sql_tool2 = "INSERT INTO  TDM_TOOLCLASSFIELDS      ( TIMESTAMP ,    TOOLCLASSID ,    TOOLCLASSFIELDSPOS ,    INDEXID ,    NAME ,    NAME01 ,    NAME2 ,    NAME201 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    FIELDTYPEEX ,    POS ,    COMPORTOOL )    VALUES   ( 1111111111 ,    ? ,    103 ,    N'1' ,    N'XS实际值' ,    N'XS实际值' ,    N'杭汽-XS实际值' ,    N'杭汽-XS实际值' ,    N'NUMBER(4,3)' ,    N'CLGR' ,    N'-' ,    0 ,    0 ,    4 ,    2 ) ";
		String sql_tool3 = "INSERT INTO  TDM_TOOLCLASSFIELDS      ( TIMESTAMP ,    TOOLCLASSID ,    TOOLCLASSFIELDSPOS ,    INDEXID ,    NAME ,    NAME01 ,    NAME2 ,    NAME201 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    FIELDTYPEEX ,    POS ,    COMPORTOOL )    VALUES   ( 1111111111 ,    ? ,    104 ,    N'1' ,    N'YS实际值' ,    N'YS实际值' ,    N'杭汽-YS实际值' ,    N'杭汽-YS实际值' ,    N'NUMBER(4,3)' ,    N'CLGR' ,    N'-' ,    0 ,    0 ,    5 ,    2 )   ";

		for (Object o : toolClassList) {
			try {
				Map m = (Map) o;
				String classID = m.get("TOOLCLASSID").toString();
				logger.info("Creating ToolClass Parameter : " + classID);
				jdbc.update(sql_item1, classID);
				jdbc.update(sql_item2, classID);

				jdbc.update(sql_tool1, classID);
				jdbc.update(sql_tool2, classID);
				jdbc.update(sql_tool3, classID);
				logger.info("Finish ToolClass Parameter : " + classID);
			} catch (SQLException e) {
				logger.error("创建刀具类别参数出错！跳过该刀具类别！CHECK IN TDM");
				logger.error(e.getMessage(), e);
			}
		}
		return true;
	}

	@SuppressWarnings({ "static-access", "rawtypes" })
	public static boolean createListParameter() {
		String sql_query_workpieceClass = "SELECT WORKPIECECLASSID, LISTTYPE FROM TMS_WORKPIECECLASS";
		List toolClassList = null;
		try {
			toolClassList = jdbc.query(sql_query_workpieceClass);
		} catch (SQLException e) {
			logger.error("查询工件类别出错！");
			logger.error(e.getMessage(), e);
			return false;
		}

		String sql_list1 = "INSERT INTO  TMS_WORKPIECECLASSFIELDS      ( TIMESTAMP ,    WORKPIECECLASSID ,    WORKPIECECLASSFIELDSPOS ,    NAME ,    NAME01 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    POS )    VALUES   ( 1111111111 ,    ? ,    100 ,    N'首次用于' ,    N'首次用于' ,    N'CHAR(100)' ,    N'TMS' ,    N'-' ,    0 ,    1 )   ";
		String sql_list2 = "INSERT INTO  TMS_WORKPIECECLASSFIELDS      ( TIMESTAMP ,    WORKPIECECLASSID ,    WORKPIECECLASSFIELDSPOS ,    NAME ,    NAME01 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    POS )    VALUES   ( 1111111111 ,    ? ,    101 ,    N'版本号' ,    N'版本号' ,    N'CHAR(100)' ,    N'TMS' ,    N'-' ,    0 ,    2 ) ";
		String sql_list3 = "INSERT INTO  TMS_WORKPIECECLASSFIELDS      ( TIMESTAMP ,    WORKPIECECLASSID ,    WORKPIECECLASSFIELDSPOS ,    NAME ,    NAME01 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    POS )    VALUES   ( 1111111111 ,    ? ,    102 ,    N'编制人' ,    N'编制人' ,    N'CHAR(100)' ,    N'TMS' ,    N'-' ,    0 ,    3 )   ";
		String sql_list4 = "INSERT INTO  TMS_WORKPIECECLASSFIELDS      ( TIMESTAMP ,    WORKPIECECLASSID ,    WORKPIECECLASSFIELDSPOS ,    NAME ,    NAME01 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    POS )    VALUES   ( 1111111111 ,    ? ,    103 ,    N'编制日期' ,    N'编制日期' ,    N'CHAR(100)' ,    N'TMS' ,    N'-' ,    0 ,    4 )   ";
		String sql_list5 = "INSERT INTO  TMS_WORKPIECECLASSFIELDS      ( TIMESTAMP ,    WORKPIECECLASSID ,    WORKPIECECLASSFIELDSPOS ,    NAME ,    NAME01 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    POS )    VALUES   ( 1111111111 ,    ? ,    104 ,    N'校对人' ,    N'校对人' ,    N'CHAR(100)' ,    N'TMS' ,    N'-' ,    0 ,    5 )   ";
		String sql_list6 = "INSERT INTO  TMS_WORKPIECECLASSFIELDS      ( TIMESTAMP ,    WORKPIECECLASSID ,    WORKPIECECLASSFIELDSPOS ,    NAME ,    NAME01 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    POS )    VALUES   ( 1111111111 ,    ? ,    105 ,    N'校对日期' ,    N'校对日期' ,    N'CHAR(100)' ,    N'TMS' ,    N'-' ,    0 ,    6 )   ";
		String sql_list7 = "INSERT INTO  TMS_WORKPIECECLASSFIELDS      ( TIMESTAMP ,    WORKPIECECLASSID ,    WORKPIECECLASSFIELDSPOS ,    NAME ,    NAME01 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    POS )    VALUES   ( 1111111111 ,    ? ,    106 ,    N'修改标记1' ,    N'修改标记1' ,    N'CHAR(100)' ,    N'TMS' ,    N'-' ,    0 ,    7 )   ";
		String sql_list8 = "INSERT INTO  TMS_WORKPIECECLASSFIELDS      ( TIMESTAMP ,    WORKPIECECLASSID ,    WORKPIECECLASSFIELDSPOS ,    NAME ,    NAME01 ,    FIELDTYPEID ,    MOD ,    FUNCTYPEID ,    UNITNR ,    POS )    VALUES   ( 1111111111 ,    ? ,    107 ,    N'修改标记2' ,    N'修改标记2' ,    N'CHAR(100)' ,    N'TMS' ,    N'-' ,    0 ,    8 )   ";

		for (Object o : toolClassList) {
			try {
				Map m = (Map) o;
				String workpieceClassID = m.get("WORKPIECECLASSID").toString();
				logger.info("Creating WORKPIECECLASS Parameter : " + workpieceClassID);
				jdbc.update(sql_list1, workpieceClassID);
				jdbc.update(sql_list2, workpieceClassID);
				jdbc.update(sql_list3, workpieceClassID);
				jdbc.update(sql_list4, workpieceClassID);
				jdbc.update(sql_list5, workpieceClassID);
				jdbc.update(sql_list6, workpieceClassID);
				jdbc.update(sql_list7, workpieceClassID);
				jdbc.update(sql_list8, workpieceClassID);
				logger.info("Finish WORKPIECECLASS Parameter : " + workpieceClassID);
			} catch (Exception e) {
				logger.error("创建WORKPIECECLASS参数出错！CHECK IN TDM");
				logger.error(e.getMessage(), e);
			}
		}
		return true;
	}
}
