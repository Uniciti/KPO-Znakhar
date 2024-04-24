import base64
import random
import uuid
import mysql.connector
from datetime import datetime
from flask import Flask, request, jsonify
import os
from PIL import Image
import io

app = Flask(__name__)

UPLOAD_FOLDER = 'images'  # Папка для сохранения загруженных изображений
if not os.path.exists(UPLOAD_FOLDER):
    os.makedirs(UPLOAD_FOLDER)

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


@app.route('/upload-image', methods=['POST'])
def upload_image():
    # Получаем объект изображения из POST-запроса
    image_file = request.files['file']
    request_id, date = id_generator()
    if image_file:
        img = Image.open(io.BytesIO(image_file.read()))
        image_base64 = img_processor(img)
        defect, recom = deGenerate()
        filename = request_id + '.jpg'
        filepath = os.path.normpath(os.path.join(app.config['UPLOAD_FOLDER'], filename))
        img.save(filepath)
        insert_into_table('localhost',
                          'serverClient',
                          'serverpass',
                          'kpo_znakhar',
                          'img_table',
                          filepath)

        return jsonify({'request_id': request_id,
                        'img': image_base64,
                        'defects': defect,
                        'recomendation': recom,
                        'date': date
                        })
    else:
        return jsonify({'request_id': request_id, 'error': 'No image provided'}), 400
def id_generator():
    request_id = str(uuid.uuid4())
    current_date_time = datetime.now()
    date = current_date_time.strftime('%Y.%m.%d-%H:%M')
    return request_id, date

def insert_into_table(host, user, password, database, table, img_src):
    conn = None
    try:
        conn = mysql.connector.connect(
            host=host,
            user=user,
            password=password,
            database=database
        )
        if conn.is_connected():
            cursor = conn.cursor()
            # Формируем SQL-запрос для вставки данных в таблицу
            insert_query = f'INSERT INTO {table} (img_src) VALUES (%s)'
            cursor.execute(insert_query, (img_src,))
            conn.commit()
            print('Данные успешно добавлены в таблицу')
        else:
            print('Ошибка подключения к базе данных MySQL')
    except mysql.connector.Error as e:
        print(f'Ошибка при добавлении данных в таблицу: {e}')
    finally:
        if conn and conn.is_connected():
            conn.close()


def deGenerate():
    defects = [
        'Разрыв сухожилий',
        'Анальная трещина',
        'Повреждение мозжечка',
        'Незачёт по экономике',
        'Инсульт жопы',
        'Зачтено',
        'HOI4 наиграно 1000 часов',
    ]
    recom = {
        'Разрыв сухожилий': 'Лечебная лоботомия',
        'Анальная трещина': 'Подорожники',
        'Повреждение мозжечка': 'Лечебная лоботомия',
        'Незачёт по экономике': 'Отрастить усы',
        'Инсульт жопы': 'Это не лечится',
        'Зачтено' : 'Победа',
        'HOI4 наиграно 1000 часов': 'Помыться',
    }
    a = random.choice(defects)
    b = recom[a]
    return a, b
def img_processor(img):
    byte_io = io.BytesIO()
    img.save(byte_io, format='JPEG')
    byte_array = byte_io.getvalue()
    image_base64 = base64.b64encode(byte_array).decode('utf-8')
    return image_base64
'''
data class PlantReportItem(
val id: Int,
val img: ByteArray,
val defects: List<String>,
val recommendations: List<String>,
val date: String
)
'''



if __name__ == '__main__':
    app.run(debug=True)
