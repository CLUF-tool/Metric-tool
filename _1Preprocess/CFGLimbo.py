import os

Project=['avro']
Version=['avro-1.2.0']
FileSum=[102]
for A, B, C in zip(Project,Version,FileSum):

    abspath = os.path.abspath('../')
    output_path = abspath + '\output' + '\\'
    project_name = B
    deps_rsf_file = output_path +B+'-deps.rsf'
    lang = "java"
    clustering_algorithm = "limbo"
    sim_measure = "ilm"
    granule = "file"
    preselected_range = "10,600,10"
    stop_criterion = "preselected"
    # print(output_path +B+"-limbo.cfg")
    with open(output_path +B+"-limbo.cfg", "w") as f:
        f.write("project_name=" + project_name+"\n")
        f.write("lang=" + lang+"\n")
        f.write("deps_rsf_file=" + deps_rsf_file+"\n")
        f.write("clustering_algorithm=" + clustering_algorithm+"\n")
        f.write("sim_measure =" + sim_measure+"\n")
        f.write("granule =" + granule+"\n")
        f.write("preselected_range =" + preselected_range+"\n")
        f.write("stop_criterion =" + stop_criterion+"\n")
    # break