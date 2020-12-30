import subprocess

if __name__ == '__main__':
    schoolOutput = open("C:\\Users\\24565\\PycharmProjects\\javaTest"
                        "\\tester_files\\tests\\zzz-test-by-school_filter054"
                        ".flt\\complex_school_solution_output.txt")
    schoolOutputContent = {x.strip() for x in schoolOutput}
    schoolOutput.close()
    avishaiOutput = open("C:\\Users\\24565\\PycharmProjects\\javaTest"
                         "\\tester_files\\tests\\test03\\complex_user_output"
                         ".txt")
    avOutputContent = {x.strip() for x in avishaiOutput}
    needToBeRead = set()
    for item in avOutputContent:
        if item not in schoolOutputContent:
            command_list = ["attrib" ,"+R",
                            "C:\\Users\\24565\\PycharmProjects\\javaTest"
                            "\\tester_files\\files_to_filter\\complex"
                            ""+"\\" + item]
            process = subprocess.Popen(command_list, stdin=subprocess.PIPE,
                             stdout=subprocess.PIPE, stderr=subprocess.PIPE,
                             universal_newlines=True)
            process.communicate()