import requests
import json

def get():
    base_url = "http://api.data.cma.cn:8090/api?userId=5508129994419wCnS&pwd=USnwibZ&dataFormat=json&interfaceId=getSurfEleByTimeRangeAndStaID&dataCode=SURF_CHN_MUL_HOR&timeRange=[20190331000000,20190331235959]&staIDs=57494&elements=Station_Id_C,Year,Mon,Day,Hour,PRS,PRS_Sea,PRS_Max,PRS_Min,TEM,TEM_Max,TEM_Min,RHU,RHU_Min,VAP,PRE_1h,WIN_D_INST_Max,WIN_S_Max,WIN_D_S_Max,WIN_S_Avg_2mi,WIN_D_Avg_2mi,WEP_Now,WIN_S_Inst_Max,tigan,windpower,VIS,CLO_Cov,CLO_Cov_Low,CLO_COV_LM"
    headers = {
        "User-Agent" : "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36",
        "Cookies" : "pgv_pvi=172854272; pgv_si=s3410934784; Hm_lvt_d9508cf73ee2d3c3a3f628fe26bd31ab=1550805883,1550806122,1550806836,1550810835; Hm_lpvt_d9508cf73ee2d3c3a3f628fe26bd31ab=1550813487"
    }
    
    

    # base_url = "http://api.data.cma.cn:8090/api?"

    # form_data = {
    #     "userId": "5508129994419wCnS",
    #     "pwd": "USnwibZ",
    #     "dataFormat": "json",
    #     "interfaceId": "getSurfEleByTimeRangeAndStaID",
    #     "dataCode": "SURF_CHN_MUL_HOR",
    #     "timeRange": "[20190216000000,20190221235959]",
    #     "staIDs": "57494",
    #     "elements": "Station_Id_C,Year,Mon,Day,Hour,PRS,PRS_Sea,PRS_Max,PRS_Min,TEM,TEM_Max,TEM_Min,RHU,RHU_Min,VAP,PRE_1h,WIN_D_INST_Max,WIN_S_Max,WIN_D_S_Max,WIN_S_Avg_2mi,WIN_D_Avg_2mi,WEP_Now,WIN_S_Inst_Max,tigan,windpower,VIS,CLO_Cov,CLO_Cov_Low,CLO_COV_LM"
    # }
    response = requests.get(url = base_url, headers = headers)
    print(response.text)

get()

