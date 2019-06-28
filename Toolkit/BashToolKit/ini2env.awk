# usage:
# tmp=`mktemp`
# cat *.ini | awk -F '=' --file ini2env.awk >> $tmp
# source $tmp
# rm $tmp

function ltrim(s) { sub(/^[ \t\r\n]+/, "", s); return s }
function rtrim(s) { sub(/[ \t\r\n]+$/, "", s); return s }
function trim(s) { return rtrim(ltrim(s)); }

BEGIN{scope="";} {                              
        if ($1 ~ /\[.*\]/)                                                   
                scope=trim(substr($1,2,length($1)-2));                             
	else {                                                   
                name=trim($1)
		value=$2;  
                if (length(value) > 0) { 
                        printf("export %s_%s=\"%s\"\n", scope, name, value); 
		} 
	} 
}
