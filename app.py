from flask import Flask
from paddleocr import PaddleOCR
from flask import request
import json
import os
import cv2
import sys
import numpy as np
import time
import uuid

from PIL import Image, ImageDraw, ImageFont

from paddleocr import PaddleOCR, draw_ocr

font=cv2.FONT_HERSHEY_SIMPLEX




#Paddleocr目前支持中英文、英文、法语、德语、韩语、日语，可以通过修改lang参数进行切换

#参数依次为`ch`, `en`, `french`, `german`, `korean`, `japan`。

ocr = PaddleOCR(use_angle_cls=True, lang="ch",use_gpu=False,

                rec_model_dir='./models/ch_ppocr_server_v2.0_rec_infer/',

                cls_model_dir='./models/ch_ppocr_mobile_v2.0_cls_infer/',

                det_model_dir='./models/ch_ppocr_server_v2.0_det_infer/') # need to run only once to download and load model into memory


app = Flask(__name__)


@app.route("/ocr", methods=['GET', 'POST'])
def ocr_api():
#     file=request.args.get('file', '')
    context=request.form.get('context', '')
    del_flag=request.form.get('del', '')

    file = request.files['file']
#     tmp_file = "d:/tmp/"+str(uuid.uuid4())+".png"
    tmp_file = "d:/tmp/"+context+".png"
    file.save(tmp_file)

    t0 = int(time.time() * 1000)

    result = ocr.ocr(tmp_file, cls=True)
    print(result)
    if del_flag=='1':
        os.remove(tmp_file)
    return json.dumps({
        'ms':int(time.time() * 1000)-t0,
        'data':result
    }, ensure_ascii=False)
