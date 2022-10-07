import os

import pandas as pd


def Export_Directory_RSF( UDPName,output_path,root_path,):
    '''
    用于外部指标，导出目录依赖
    contain 目录 文件名
    '''
    # filename_path=gl.FirstPathGithub + "-filename.csv"

    Directory_label = pd.read_csv(output_path + UDPName + '-filename.csv', names=['path'])
    # print(Directory_label)
    # print(gl.RootPathGithub+'\\')
    Directory_label['path'] = Directory_label['path'].str.replace(root_path+'\\', '', regex=False)
    Directory_label['path'] = Directory_label['path'].str.replace('\\', '.', regex=False)
    Directory_label['Arch']=Directory_label['path'].str.extract(r'([^\s]*(?=\.src|org\.))',expand=False)
    # print(Directory_label)

    RSF_Data = Directory_label[['Arch', 'path']]
    RSF_Data.insert(0, 'contain', 'contain')

    RSF_Data.to_csv(output_path +UDPName+'-dir-gt.rsf', sep=' ', index=None, header=None)




if __name__ == '__main__':
    # .udb文件所在路径
    abspath = os.path.abspath('../')
    udp_path=abspath+r'\example_project\avro-1.2.0'
    # .udb文件名
    udb_project_name='avro-1.2.0'
    # 项目根目录，一般和udp_path相同。可查看UDPath + UDPName + '-filename.csv'查看实际根目录。
    root_path=os.path.abspath('../')
    # 输出路径
    output_path=abspath+'\output'+'/'

    Export_Directory_RSF(udb_project_name, output_path,root_path)

