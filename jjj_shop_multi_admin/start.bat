
@echo off
:: nodejs安装目录下的nodevars.bat
set nodevars = "D:\Program Files\nodejs\nodevars.bat"
:: 切换到D盘
d:
:: 移动到需要启动的目录
cd product_java/jjj_shop_multi_git_java/jjj_shop_multi_git_admin
:: 启动项目
cmd /c %nodevars%&&npm run dev