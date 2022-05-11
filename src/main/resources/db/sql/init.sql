-- 初始化菜单
INSERT INTO sys_menu VALUES (REPLACE(UUID(),'-',''),'应用管理',null,'/app-management',0,'M',1,1,'','',now(),now(),'init','init',0);
INSERT INTO sys_menu VALUES (REPLACE(UUID(),'-',''),'应用列表',(SELECT id from (SELECT id from sys_menu WHERE path = '/app-management') t1),'/app-management/list',0,'F',1,1,'','',now(),now(),'init','init',0);
INSERT INTO sys_menu VALUES (REPLACE(UUID(),'-',''),'应用类型',(SELECT id from (SELECT id from sys_menu WHERE path = '/app-management') t1),'/app-management/type',0,'F',1,1,'','',now(),now(),'init','init',0);
INSERT INTO sys_menu VALUES (REPLACE(UUID(),'-',''),'接入申请列表',(SELECT id from (SELECT id from sys_menu WHERE path = '/app-management') t1),'/app-management/request-list',0,'F',1,1,'','',now(),now(),'init','init',0);
INSERT INTO sys_menu VALUES (REPLACE(UUID(),'-',''),'模块组件管理',null,'/component-management',0,'F',1,1,'','',now(),now(),'init','init',0);
INSERT INTO sys_menu VALUES (REPLACE(UUID(),'-',''),'系统设置',null,'/system-setting',0,'M',1,1,'','',now(),now(),'init','init',0);
INSERT INTO sys_menu VALUES (REPLACE(UUID(),'-',''),'用户管理',(SELECT id from (SELECT id from sys_menu WHERE path = '/system-setting') t1),'/system-setting/user',0,'f',1,1,'','',now(),now(),'init','init',0);
INSERT INTO sys_menu VALUES (REPLACE(UUID(),'-',''),'角色管理',(SELECT id from (SELECT id from sys_menu WHERE path = '/system-setting') t1),'/system-setting/role',0,'f',1,1,'','',now(),now(),'init','init',0);
INSERT INTO sys_menu VALUES (REPLACE(UUID(),'-',''),'菜单管理',(SELECT id from (SELECT id from sys_menu WHERE path = '/system-setting') t1),'/system-setting/menu',0,'f',1,1,'','',now(),now(),'init','init',0);

-- 初始化菜单
INSERT INTO sys_role VALUES (REPLACE(UUID(),'-',''),'admin','管理员',0,now(),now(),'init','init',0);



-- 初始化网站
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'1',0,0,'东部战区','http://www.qjw.db',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'2',0,0,'南部战区','http://www.qjw.nb',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'3',0,0,'西部战区','http://www.qjw.xb',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'4',0,0,'北部战区','http://www.qjw.bb',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'5',0,0,'中部战区','http://www.qjw.zb',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'6',0,0,'陆军','http://www.qjw.lj',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'7',0,0,'海军','http://www.qjw.hj',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'8',0,0,'空军','http://www.qjw.kj',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'9',0,0,'火箭军','http://www.qjw.hjj',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'10',0,0,'战略支援部队','http://www.qjw.zy',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'11',0,0,'联勤保障部队','http://27.144.32.99',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'12',0,0,'军事科学院','http://www.qjw.jk',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'13',0,0,'国防大学','http://www.gfdxqjw.mtn',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'14',0,0,'国防科技大学','http://www.gfkdqjw.mtn',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'15',0,0,'武装警察部队','http://www.qjw.wj',now(),now(),'init','init',0);

INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'index',1,0,'首页','/',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'training-mgt-info',1,0,'训管资讯','/consulting-service',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'data-source',1,0,'数据资源','http://21.18.2.155:8091/#/databuilder/design/formdesign/index',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'data-application',1,0,'数据应用','http://21.18.2.159:10003',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'service-application',1,0,'服务应用','/appStore',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'personal-center',1,0,'个人中心','/personal-center',now(),now(),'init','init',0);


INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'set-as-home-page',2,0,'设为首页','/',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'eportal-mgt',2,0,'后台管理','http://18.91.21.138/eportal-mgt',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'ecms-mgt',2,0,'信息发布','http://18.91.21.138/ecms-mgt',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'oauth-center',2,0,'认证中心','http://18.91.21.138:9100/page/entry.html',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'help-center',2,0,'帮助中心','http://18.91.21.138/help-centerl',now(),now(),'init','init',0);
INSERT INTO website VALUES (REPLACE(UUID(),'-',''),'vision-system',2,0,'视觉识别','http://18.91.21.138/vision-system',now(),now(),'init','init',0);


-- 初始化组件
INSERT INTO component VALUES (REPLACE(UUID(),'-',''),'focus-news','训管要闻','1',0,0,null,null,3, 10,1,null,1,1,1,0,now(),now(),'init','init',0);
INSERT INTO component_personal_config VALUES(REPLACE(UUID(),'-',''),(SELECT id from (SELECT id from component WHERE code = 'focus-news') t1),0,null,0,0,1,10);

INSERT INTO component VALUES (REPLACE(UUID(),'-',''),'notice-announcement','测试',null,0,0,null,null,3, 10,1,null,1,1,1,0,now(),now(),'init','init',0);
INSERT INTO component_personal_config VALUES(REPLACE(UUID(),'-',''),(SELECT id from (SELECT id from component WHERE code = 'notice-announcement') t1),0,null,0,10,3,10);

INSERT INTO component VALUES (REPLACE(UUID(),'-',''),'real-time-info','通知公告',null,0,0,null,null,3, 10,1,null,1,1,1,0,now(),now(),'init','init',0);
INSERT INTO component_personal_config VALUES(REPLACE(UUID(),'-',''),(SELECT id from (SELECT id from component WHERE code = 'real-time-info') t1),0,null,0,10,3,10);

INSERT INTO component VALUES (REPLACE(UUID(),'-',''),'microservices','微服务',null,0,2,null,null,3, 10,1,null,1,1,1,0,now(),now(),'init','init',0);
INSERT INTO component_personal_config VALUES(REPLACE(UUID(),'-',''),(SELECT id from (SELECT id from component WHERE code = 'microservices') t1),0,null,0,10,3,10);


-- -- 初始化组件类型
-- INSERT INTO app_type VALUES (REPLACE(UUID(),'-',''),'comprehensive-application','综合应用',now(),now(),'init','init',0);
-- INSERT INTO app_type VALUES (REPLACE(UUID(),'-',''),'military-training','军事训练',now(),now(),'init','init',0);
-- INSERT INTO app_type VALUES (REPLACE(UUID(),'-',''),'military-education','军事教育',now(),now(),'init','init',0);
-- INSERT INTO app_type VALUES (REPLACE(UUID(),'-',''),'force-management','部队管理',now(),now(),'init','init',0);
-- INSERT INTO app_type VALUES (REPLACE(UUID(),'-',''),'daily-office','日常办公',now(),now(),'init','init',0);
--
