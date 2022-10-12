# Metric-Tool GUIDE
## 1.工具介绍
1. 预处理工具

位置：\Metric-Tool\_1Preprocess

2. Arcade

位置：\Metric-Tool\_2arcade
其中已修改且可用的聚类算法为：ACDC、ARC、Limbo、WCA。

源码地址：[https://github.com/usc-softarch/arcade_core](https://github.com/usc-softarch/arcade_core)

文献参考：Marcelo Schmitt Laser, Nenad Medvidovic, Duc Minh Le, Joshua Garcia: ARCADE: an extensible workbench for architecture recovery, change, and decay evaluation. ESEC/SIGSOFT FSE2020: 1546-1550

3. 后处理工具

位置：\Metric-Tool\_3Postprocess

4. 评估指标工具

位置：\Metric-Tool\_4Metrics
项目名，其中可用的外部指标为：MoJo、A2A、C2C
## 2.预处理阶段
#### A.用于ACDC
##### <project>_deps.rsf
运行CLUF/utils/FileDpesOutput.py中的Export_Dependecy_RSF()函数，提取deps.rsf文件。
在这个python文件中，配置好undertand生成项目的udb文件的路径，确保可以访问到这个文件。

PS：这个Export_Dependecy_RSF()函数主要是用程序导出文件之间的依赖，对应手动操作understand，选择Reports-Dependency-File Dependency，导出文件依赖的csv文件后解析生成deps.rsf文件。也可以根据实际需要导出架构、类之间的依赖等。
#### B.用于ARC
##### <project>_deps.rsf
同上
##### infer.mallet、output.pipe、topicmodel.data
配置并运行arcade\test\ARC_Base.java，将获得infer.mallet、output.pipe、topicmodel.data。但是注意，一些项目文件数比较多，可能会导致运行报错：Out of memory。
执行ARC_Base.java会把命令行语句输出到输出面板。如果对于某一个项目，ARC_Base.java没有生成这3个文件中的某一个，那么就把相应的命令行拷贝到CMD执行。注意去掉前缀：cmd /c 。
#### C.用于Limbo
##### <project>-limbo.cfg
运行CLUF/utils/CFGLimbo.py，导出.cfg文件
#### D.用于WCA
##### <project>-wca-uemnm.cfg
运行CLUF/utils/CFGWCA.py，导出.cfg文件
## 3.架构提取阶段
### A.环境配置
把idea中的sdk换成java1.8，避免出现——java: 程序包****不存在。
### B.输入配置
#### 公共输入
源代码目录
输出目录
##### ACDC

1. <project>_deps.rsf
##### ARC

1. <project>_deps.rsf
2. infer.mallet
3. output.pipe
4. topicmodel.data
##### Limbo

1. <project>-limbo.cfg
##### WCA

1. <project>-wca-uemnm.cfg
### C.输出
每种聚类方法生成对应的聚类结果/架构

其中WCA和Limbo的结果将会被输出到\Metric-Tool\_2arcade\data文件夹下
### D.运行
##### ACDC

1. 运行arcade\test\ACDC_Test.java，批量处理多个项目
##### ARC

1. 运行arcade\test\ARC_Test.java，批量处理多个项目
##### Limbo

1. 运行arcade\test\Limbo_Test.java，批量处理多个项目
##### WCA

1. 运行arcade\test\WCA_Test.java，批量处理多个项目
## 4.后处理阶段
配置好输入输出，得到目录结构作为Ground Truth。
## 5.指标评估阶段
在MetricDriver.py中配置好ground_truth_rsf、cluster_rsf路径，运行即可得到指标结果。
