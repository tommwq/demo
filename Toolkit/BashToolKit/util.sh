
# count_process(result, process_name)
function count_process()
{
    if [ $# -eq 2 ]; then
        local result=$1
        local process_name=$2
        local cnt=`ps -ef | grep ${process_name} | grep -v grep | wc -l`
        eval $result="'$cnt'"
    fi
}

# invoke_script_when_exists(script_filename)
function invoke_script_when_exists()
{
    if [ $# -eq 1 ]; then
        local script=$1
        
        if [ -f $script ]; then
            source $script
        fi
    fi
}

# output(message, is_show=true)
function output()
{
    local is_show=1
    if [ $# -eq 2 ]; then
        if [ $2 -eq 0 ]; then
            is_show=0
        fi
    fi

    if [ $is_show ]; then
        local message=$1
        echo $message
    fi
}

# peak_process_id(result, process_name)
# return "" if not found
function peak_process_id()
{
    if [ $# -eq 2 ]; then
        local result=$1
        local process_name=$2;
	local id=`ps -ef | grep ${process_name} | grep -v grep | awk '{print $2}' | head -1`
        eval $result="'$id'"
    fi
}

# get_process_id(result, process_name)
# return id list such as "123 456"
function get_process_id()
{
    if [ $# -eq 2 ]; then
        local result=$1
        local process_name=$2;
        local pid_list=`ps -f | grep ${process_name} | awk '{print $2}' | grep -v PID | xargs`
        eval "$result='$pid_list'"
    fi
}

# get_current_date(result)
function get_current_date()
{
    if [ $# -eq 1 ]; then
        local result=$1
        local date=$((`date +"%Y%m%d"`))
        eval $result="'$date'"
    fi
}

# get_current_time(result)
function get_current_time()
{
    if [ $# -eq 1 ]; then
        local result=$1
        local date=$((`date +"%k%M%S"`))
        eval $result="'$date'"
    fi
}
