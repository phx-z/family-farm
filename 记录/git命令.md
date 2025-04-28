# git命令：

* 添加远程仓库地址：
  `git remote add origin url`
* 修改远程仓库地址：
  `git remote set-url origin url`
* 查看当前远程仓库地址:
  `git remote -v`
* 查看idea中执行的git命令的历史记录：
* ` history`

# 关联多个远程仓库‌（GitHub + Gitee 并行维护）

本地仓库同时添加 GitHub 和 Gitee 地址

### 关联多个远程仓库

* 添加github远程仓库地址 `git remote add origin url`
* 添加gitee远程仓库地址 `git remote add gitee url`

### 同步推送代码

* `git push # 推送到 GitHub`
* `git push origin main # 推送到 GitHub`
* `git push gitee # 推送到 Gitee`
* `git push gitee main # 推送到 Gitee`

