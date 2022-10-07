import os
from ExternalMetricSourceCode.C2C.C2C import C2C

def execCmd(cmd):
    # print(cmd)
    r = os.popen(cmd)
    text = r.read()
    r.close()
    return text

if __name__ == '__main__':
    # dependency_rsf=r"Input\archstudio-dependency.rsf"
    # ground_truth_rsf=r"Input\archstudio_gt.rsf"
    # cluster_rsf=r"Input\archstudio-4.0_cls.rsf"

    # dependency_rsf = r"D:\program\SC_Related\Metric-Tool\output\avro-1.2.0-deps.rsf"
    ground_truth_rsf = r"D:\program\SC_Related\Metric-Tool\output\avro-1.2.0-dir-gt.rsf"
    cluster_rsf = r"D:\program\SC_Related\Metric-Tool\output\ACDC\avro-1.2.0_acdc_clustered.rsf"

    MoJoFM=execCmd("java -jar ExternalMetricSourceCode/MoJo/mojo.jar "+ground_truth_rsf+" "+cluster_rsf+' -fm')
    A2A=execCmd("java -jar ExternalMetricSourceCode/A2A/A2A.jar "+ground_truth_rsf+" "+cluster_rsf)
    c2c=C2C(ground_truth_rsf,cluster_rsf)

    print("External metrics: ")
    print("MoJoFM:",float(MoJoFM))
    print("A2A:",float(A2A))
    print("C2C:",c2c)
