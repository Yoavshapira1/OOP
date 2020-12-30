"""
SecondaryTester for OOP, exercise 5.
"""
import textwrap
from os import listdir
# from os import startfile
from os.path import isfile, join, isdir, exists
from os import path
import subprocess

tester_ver = 1.3
ASCII_ERROR = """
______  _______  _______  _______  _______ 
(  ____ \(  ____ )(  ____ )(  ___  )(  ____ )
| (    \/| (    )|| (    )|| (   ) || (    )|
| (__    | (____)|| (____)|| |   | || (____)|
|  __)   |     __)|     __)| |   | ||     __)
| (      | (\ (   | (\ (   | |   | || (\ (   
| (____/\| ) \ \__| ) \ \__| (___) || ) \ \__
(_______/|/   \__/|/   \__/(_______)|/   \__/
"""
ASCII_PASSES = """
  , ; ,   .-'^^^'-.   , ; ,
  \\|/  .'         '.  \|//
   \-;-/   ()   ()   \-;-/
   // ;               ; \\
  //__; :.         .; ;__\\
 `-----\'.'-.....-'.'/-----'
        '.'.-.-,_.'.'
          '(  (..-'
            '-'
"""

# paths to files and folder
path_to_test_files = path.join("tester_files")
path_to_tests = path.join(path_to_test_files, "tests")
path_to_type_2_tests = path.join(path_to_test_files, "tests_error_type_2")
path_to_files_to_filter = path.join(path_to_test_files, "files_to_filter")

folders_of_files_to_filter = ['simple', 'complex']

path_to_java_files = path.join("src")
path_to_compiled_files = path.join(path_to_test_files, "compiled_files")
path_to_DirectoryProcessor_compiled = path.join("filesprocessing.DirectoryProcessor")
path_to_filesprocessing = path.join(path_to_java_files, "filesprocessing")
path_to_DirectoryProcessor_not_compiled = path.join(path_to_filesprocessing,
                                                    "DirectoryProcessor.java")

# name of files
name_of_user_output_file_no_folder = "_user" + "_output" + ".txt"
name_of_user_errors_file_no_folder = "_user" + "_errors" + ".txt"
name_of_commands_file = "commands.txt"
name_of_school_solution_output_no_folder = "_school_solution" + "_output" + ".txt"
name_of_school_solution_errors_no_folder = "_school_solution" + "_errors" + ".txt"
name_of_p_file = path.join(path_to_files_to_filter, folders_of_files_to_filter[1], "99..size-8780.NHDR.png")


def can_not_test(msg):
    print("Error:", msg)
    input("press Enter to exit")
    exit(1)


def run_with_cmd(command_list):
    """
    Execute the given command list with the command line
    Return a tuple containing the return code, output and errors.
    """

    process = subprocess.Popen(command_list, stdin=subprocess.PIPE,
                               stdout=subprocess.PIPE, stderr=subprocess.PIPE, universal_newlines=True)

    output, errors = process.communicate()
    return process.returncode, output, errors


def print_with_indentation(title, to_print):
    """print text in a nice way"""
    prefix = title + ": "
    wrapper = textwrap.TextWrapper(initial_indent=prefix, break_long_words=False,
                                   subsequent_indent=' ' * len(prefix))
    print(wrapper.fill(to_print))


def compile_file():
    """
    compile the java files. the compiled files are located in \test_files\compiled_files.
    terminate the tester if there was an error.
    :return: true if was successful.
    """
    if not exists(path_to_java_files) or not isdir(path_to_java_files):
        can_not_test("You don't have the folder: " + path_to_java_files)
    if not exists(path_to_DirectoryProcessor_not_compiled):
        can_not_test("You don't have the file: " + path_to_DirectoryProcessor_not_compiled)
    if not exists(path_to_filesprocessing) or not isdir(path_to_filesprocessing):
        can_not_test("You don't have the folder: " + path_to_filesprocessing)
    if not exists(path_to_DirectoryProcessor_not_compiled):
        can_not_test("You don't have the file: " + path_to_DirectoryProcessor_not_compiled)

    command_list = ['javac', '-d', path_to_compiled_files,
                    '-cp', path_to_java_files, path_to_DirectoryProcessor_not_compiled]

    code, output, errors = run_with_cmd(command_list)
    if code != 0:
        can_not_test("problem with compiling\n"+"error message\n"+errors)
    print("compile OK\n\n")


def run_one_test(test_folder_name, files_to_filter_folder):
    """
    run one test. run your code with command file given and with the data given. sva the rusolts in a txt file.
    then compare it to the school solution txt file.
    :param test_folder_name: the name of the folder of the test (tests/test#)
    :param files_to_filter_folder: the data folder  (simple of complex)
    :return: true if was successful.
    """
    path_to_test_folder = path.join(path_to_tests, test_folder_name)
    path_to_command_file = path.join(path_to_test_folder, name_of_commands_file)

    name_of_school_solution_output = files_to_filter_folder + name_of_school_solution_output_no_folder
    name_of_school_solution_errors = files_to_filter_folder + name_of_school_solution_errors_no_folder

    path_to_school_solution_output = path.join(path_to_test_folder, name_of_school_solution_output)
    path_to_school_solution_errors = path.join(path_to_test_folder, name_of_school_solution_errors)

    name_of_user_output = files_to_filter_folder + name_of_user_output_file_no_folder
    name_of_user_errors = files_to_filter_folder + name_of_user_errors_file_no_folder

    path_to_user_output = path.join(path_to_test_folder, name_of_user_output)
    path_to_user_errors = path.join(path_to_test_folder, name_of_user_errors)

    path_to_files_to_filter_folder = path.join(path_to_files_to_filter, files_to_filter_folder)

    print("starting", test_folder_name, "with data folder", files_to_filter_folder+"..")

    command_list = ["java", '-cp', path_to_compiled_files, path_to_DirectoryProcessor_compiled,
                    path_to_files_to_filter_folder,
                    path_to_command_file]

    code, user_output, user_errors = run_with_cmd(command_list)  # run your code

    # save the files output and errors
    with open(path_to_user_output, 'w') as output_file:
        output_file.write(user_output)

    with open(path_to_user_errors, 'w') as errors_file:
        errors_file.write(user_errors)

    # compare to school solution
    compare_outputs = compare_files(path_to_school_solution_output, path_to_user_output)
    compare_errors = compare_files(path_to_school_solution_errors, path_to_user_errors)

    # print helpful information if there was mistakes.
    if compare_outputs is not None or compare_errors is not None:
        # compare failed
        if compare_outputs is not None:
            print("Output file compare failed: here are the details:")
            print_with_indentation("output compare", compare_outputs)
        if compare_errors is not None:
            print("Errors file compare failed: here are the details:")
            print_with_indentation("errors compare", compare_errors)
        return False
    print("passed :)")
    return True


def compare_files(file1, file2):
    """
    compare to files with FC (windows file comparer)
    :param file1:
    :param file2:
    :return: the compaction text if there was errors
    """
    command_to_compare = ['fc', '/W', '/N', '/A', file1, file2]
    code, output, errors = run_with_cmd(command_to_compare)
    if code != 0:  # if code != 0
        return output
    return None


def run_tests():
    """
    run all the test in the folder test_files\tests with both data folders (simple, complex)
    :return: true iff all passed
    """
    all_passed = True
    tests_names = [t for t in listdir(path_to_tests) if isdir(join(path_to_tests, t))]  # list of test names
    number_of_tests = len(tests_names) * len(folders_of_files_to_filter)
    passed_tests = 0
    for folder_to_filter in folders_of_files_to_filter:  # the two data folders
        print("##########  testing with data -", folder_to_filter, "  ##########\n")
        for test_name in tests_names:  # each test
            if run_one_test(test_name, folder_to_filter):
                passed_tests += 1
            else:
                all_passed = False
            print()
        print()
    if all_passed:
        print("All tests passed!!")
        return True
    else:
        print("passes", passed_tests, "out of", number_of_tests, "tests")
        return False


def run_type_2_tests():
    """
    run all type-2 tests
    :return: true iff all passed
    """
    print("\n##########   type-2 error tests:  ##########\n")
    folder_to_filter = path.join(path_to_files_to_filter, folders_of_files_to_filter[0])
    path_to_test = path.join(path_to_type_2_tests, "no_file_with_that_name.txt")
    passed1 = run_one_type_2_test(folder_to_filter, path_to_test, "no_command_file")

    folder_to_filter = path.join(path_to_files_to_filter, folders_of_files_to_filter[0])
    path_to_test = path.join(path_to_type_2_tests, "no_file_with_that _name.txt")
    passed2 = run_one_type_2_test(folder_to_filter, path_to_test, "too many parameters")

    folder_to_filter = path.join(path_to_files_to_filter, folders_of_files_to_filter[0])
    path_to_test = ""
    passed3 = run_one_type_2_test(folder_to_filter, path_to_test, "only 1 parameter")

    return type_2_tests() and passed1 and passed2 and passed3


def type_2_tests():
    """
    run all the test in the folder test_files\tests_error_type_2
    :return: true iff all passed
    """
    all_passed = True
    tests_names = [f for f in listdir(path_to_type_2_tests) if isfile(join(path_to_type_2_tests, f))]
    number_of_tests = len(tests_names)
    passed_tests = 0
    for test in tests_names:
        folder_to_filter = path.join(path_to_files_to_filter, folders_of_files_to_filter[0])
        path_to_test = path.join(path_to_type_2_tests, test)
        passed = run_one_type_2_test(folder_to_filter, path_to_test, test)
        if passed:
            passed_tests += 1
        else:
            all_passed = False
    if all_passed:
        print("All type-2 error tests passed!!")
        return True
    else:
        print("passes", passed_tests, "out of", number_of_tests, "type-2 error tests")
        return False


def passed_all():
    print(ASCII_PASSES)
    print("you passed everything!!! \ngo get some sleep")
    # startfile(name_of_p_file)


def run_one_type_2_test(folder_to_filter, path_to_test, test):
    """run one type-2 test"""
    command = ["java", '-cp', path_to_compiled_files, path_to_DirectoryProcessor_compiled,
               folder_to_filter,
               path_to_test]
    code, output, errors = run_with_cmd(command)
    if output or not errors:
        print("test-type-2", test, "failed. nothing should be printed as filtered. and an error should "
                                   "be printed. the print was:\n")
        print_with_indentation("output", output)

        print_with_indentation("errors", errors)
        print("\ncontinue...\n")
        passed = False
    else:
        print("test-type-2", test, "passed")
        passed = True
    return passed


if __name__ == "__main__":
    while True:
        print("starting tester for ex5 version", tester_ver, '\n')
        compile_file()
        tests_passed = run_tests()
        type_2_passed = run_type_2_tests()

        if tests_passed and type_2_passed:
            passed_all()
        else:
            print(ASCII_ERROR)

        input("press enter to restart the tester")
        print('\n\n\nRestarting...')
