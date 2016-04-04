var MAIN_ID="mainClient"
,HTML_ID="Share"
,H_OFFSET=100
,W_OFFSET=18
,HEIGHT=0
,WIDTH=0
,GRID_TIME_OUT=30000
,HIDE_CSS="x-hide-display"
,ALF_TOKEN='Alfresco-CSRFToken'
,MAIN_CONTEXT="/share"
,CONTEXT=MAIN_CONTEXT+"/service"
,ALF_CONTEXT=MAIN_CONTEXT+"/proxy/alfresco/pb"
,APP_FOLDER=MAIN_CONTEXT+'/res/app'
,SEARCH_RE_ADMIN = /[{}?\[\]<>=%*|'"]/
,TEXT_REGX_ADMIN = /^[^\{\}\?\[\]\<\>\=\%\*\'\"\|]?[^\{\}\?\[\]\<\>\=\%\*\'\"\|]+$/
,TEXT_REGX_ADMIN_JOB = /^[^\{\}\[\]\<\>\=\%\'\"\|]?[^\{\}\[\]\<\>\=\%\'\"\|]+$/
,TEXT_REGX_TEXT_ADMIN = "Not allowed special characters: { } ? [ ] < > = % * ' \" |"
,EMTY_TEXT_COMBO = '--- Please Select ---'
,PAGE_SIZE = 20
,ADMIN_PAGE_SIZE = 20
,MEETING_INCREMENT_TIME = 15
,START_MEETING_TIME = '7:00 AM'  // format 'h:mm A'
,END_MEETING_TIME = '8:00 PM'  // format 'h:mm A'
,TEXT_REGX = /^[^\'\"\,]?[^\'\"\,]+$/
,TEXT_REGX_TEXT = "Not allowed special characters: ' \" \,"	
,MAIN_PR_ID = "ext-pr-form"
,REGEX = /^((?![']).)*$/
,REGEX_TEXT = "Not allowed special characters: '"	
,MAX_APPROVER = 15
,CKEDITOR_BASEPATH=MAIN_CONTEXT+"/res/page/ckeditor/"
,MODULE_ADMIN="admin"
,MODULE_PCM="pcm"
,MODULE_EXP="exp"
;