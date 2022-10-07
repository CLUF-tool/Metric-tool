import os
import pandas as pd
import understand

def execCmd(cmd):
    # print(cmd)
    r = os.popen(cmd)
    text = r.read()
    r.close()
    return text

def ReportFileDeps(UDPath,UDBProjectName,output_path):
    # UDPath = gl.DTPath + '\\' + CouplingType + '\\'
    db = understand.open(UDPath+'\\'+UDBProjectName+ '.udb')
    # print(db)
    cluster_nums = 0
    for i in db.root_archs():
        for arch_i in i.children():
            cluster_nums += 1

    CD = []
    count=0
    # print(cluster_nums)

    for file in db.ents('File'):
        # print(file.long)
        # print(db.archs(file))
        java_file = str(file.longname(True))

        if not java_file.endswith('.java'):
            continue
        # print(java_file)
        # print(file)
        [a] = db.archs(file)
        # print(a)
        From_File=java_file
        # From_File=From_File.replace("Directory ","")
        # print(From_File)

        # print(str(a))
        a_dict = file.depends()
        # print(a_dict)
        for key, value in a_dict.items():

            # ClusterDependecy.append(str(From_File))
            # print()
            [b] = db.archs(key)
            To_File=str(key.longname(True))
            # To_File=To_File.replace("Directory ","")
            ClusterDependecy = [From_File,To_File,len(value)]
            # ClusterDependecy.append(len(value))
            CD.append(ClusterDependecy)

    CD = pd.DataFrame(CD)
    CD.to_csv(output_path + UDBProjectName + '-FileDeps.csv', index=False, header=['From File','To File','Refs'])
    return CD, cluster_nums

def Export_Dependecy_RSF(UDBProjectName,root_path,output_path):
    '''
    用于内部指标，导出文件依赖
    depends 文件A 文件B
    '''

    # ReportFileDeps(UDPath)

    FileDependency = pd.read_csv(output_path+UDBProjectName + '-FileDeps.csv')

    FileDependency['From File'] = FileDependency['From File'].str.replace(root_path + '\\', '', regex=False)
    FileDependency['From File'] = FileDependency['From File'].str.replace('\\', '.', regex=False)

    FileDependency['To File'] = FileDependency['To File'].str.replace(root_path + '\\', '', regex=False)
    FileDependency['To File'] = FileDependency['To File'].str.replace('\\', '.', regex=False)

    SubFromTo=FileDependency[['From File','To File']]

    SubFromTo.insert(0, 'depends', 'depends')
    # print(SubFromTo)
    SubFromTo.to_csv(output_path +udb_project_name+'-deps.rsf', sep=' ', index=None, header=None)

def ExportFilePathForLexer(UDPRootPath,UDPName,output_path):
    UDPath = UDPRootPath + '/'
    # print(os.path.abspath(UDPath + UDPName + '.udb'))
    # print(output_path + UDPName + '-filename.csv')
    db = understand.open(os.path.abspath(UDPath + UDPName + '.udb'))

    absolute_filename=[]
    for file in db.ents('File'):


        java_file=str(file.longname(True))

        if not java_file.endswith('.java'):
            continue
        absolute_filename.append(file.longname(True))

    absolute_filename=pd.DataFrame(absolute_filename)
    absolute_filename.to_csv(output_path + UDPName + '-filename.csv',index=False,header=False)

if __name__ == '__main__':

    # .udb文件所在路径
    abspath = os.path.abspath('../')
    udp_path=abspath+r'\example_project\avro-1.2.0'
    # .udb文件名
    udb_project_name='avro-1.2.0'
    # 项目根目录，一般和udp_path相同。可查看UDPath + UDPName + '-filename.csv'查看实际根目录。
    root_path=os.path.abspath('../')

    output_path=abspath+'\output'+'/'

    ExportFilePathForLexer(udp_path,udb_project_name,output_path)
    ReportFileDeps(udp_path,udb_project_name,output_path)
    Export_Dependecy_RSF(udb_project_name,root_path,output_path)