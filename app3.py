from flask import Flask, request
from paddleocr import PaddleOCR, draw_ocr
import json
import pandas as pd
import sys
import datetime
import numpy as np
import numpy_financial as npf
from dateutil.relativedelta import relativedelta
import math
import warnings

import uuid


warnings.filterwarnings('ignore')


pd.set_option('display.max_rows', None)
pd.set_option('display.max_columns', None)
np.set_printoptions(edgeitems=5, precision=3, suppress=True)


app = Flask(__name__)


# POST方法访问
@app.route("/test", methods=["POST"])

def check():

    # 默认返回内容
    return_result = {'return_code': '200', 'return_info': '处理成功', 'result': False}

    # 判断传入的json数据是否为空
    if request.get_data() is None:
    # if not request.get_data():
        return_result['return_code'] = '5004'
        return_result['return_info'] = '请求参数为空'
        return json.dumps(return_result, ensure_ascii=False)

    # 获取传入的参数
    get_Data = request.get_data()

    # 传入的参数为bytes类型，需要转化成json



    # 对参数进行操作
    # king 新增user_code
    return json.dumps(return_result, ensure_ascii=False)

@app.route('/ocr', methods=['GET', 'POST'])
def upload_file():
    #保存文件
    if request.method == 'POST':
        file = request.files['file']
        fileType = request.form.get('fileType')
        print("文件类型:"+fileType)
        tmpFilenamePrefix = str(uuid.uuid4());
        print("tmpFilenamePrefix:"+tmpFilenamePrefix)
        tmpFilenamePrefix = tmpFilenamePrefix.replace("-","")
        tmpFilename = "/opt/" +tmpFilenamePrefix + "." + fileType
        file.save(tmpFilename)
    # 默认返回内容
    return_result = {'code': '200', 'message': '处理成功', 'data': ''}


    #ocr识别结果文字
    ocr_content = []


    #OCR

    # Paddleocr目前支持的多语言语种可以通过修改lang参数进行切换
    # 例如`ch`, `en`, `fr`, `german`, `korean`, `japan`
    ocr = PaddleOCR(use_angle_cls=True, lang="ch")  # need to run only once to download and load model into memory
    img_path = tmpFilename
    result = ocr.ocr(img_path, cls=True)
    for line in result:
        print("line:")
        print(line)
        for tmpTwo in line:
          print("tmpTwo:")
          print(tmpTwo)
          for tmpThree in tmpTwo:
              print("tmpThree:")
              print(tmpThree)
              finalArr = str(tmpThree).split(",")
              finalStr = finalArr[0].replace("'","").replace("(","")
              print("finalStr:"+finalStr)
              if finalStr.find("[") < 0:
                ocr_content.append(finalStr)

    # 显示结果
    from PIL import Image

    image = Image.open(img_path).convert('RGB')
    boxes = [line[0] for line in result]
    txts = [line[1][0] for line in result]
    scores = [line[1][1] for line in result]
    #im_show = draw_ocr(image, boxes, txts, scores, font_path='./fonts/simfang.ttf')
    #im_show = Image.fromarray(im_show)
    #im_show.save('result.jpg')


    print("ocr_content:")
    print(ocr_content)


    return_result = {'code': '200', 'message': '处理成功', 'data': ocr_content}
    return json.dumps(return_result, ensure_ascii=False)



if __name__ == "__main__":

    app.run(host="0.0.0.0", port="5000", debug=True)





