import pandas as pd

def C2C(rsf_GT,rsf_Cluster):
    threshold_cvg = 0.8
    # rsf_GT = r"D:\program\code\python\ClusterMetrics\Input\archstudio-4.0_cls.rsf"
    # rsf_Cluster = r"D:\program\code\python\ClusterMetrics\Input\archstudio-4.0_cls_1.rsf"

    GT = pd.read_csv(rsf_GT, sep=" ", names=["contain", "Cluster_name", "File"])
    Cluster = pd.read_csv(rsf_Cluster, sep=" ", names=["contain", "Cluster_name", "File"])

    dict_GT=GT.groupby('Cluster_name').File.apply(set).to_dict()
    dict_cluster=Cluster.groupby('Cluster_name').File.apply(set).to_dict()

    simC=0
    for key1,value1 in dict_GT.items():
        for key2,value2 in dict_cluster.items():
            c2c_=len(value1.intersection(value2))/max(len(value1),len(value2))
            if c2c_>threshold_cvg:
                simC+=1

    c2c_percent=simC/len(dict_cluster)
    return c2c_percent
