import requests

url = 'http://localhost:5000/upload-image'

#image_path = 'G:/data/картинки/5SkCn2fu_4x.jpg'
#image_path = 'G:/data/картинки/aaaaa.jpg'
image_path = 'G:/data/картинки/da.jpg'


# Отправляем POST запрос на сервер с изображением в теле запроса
files = {'file': open(image_path, 'rb')}
try:
    response = requests.post(url, files=files)

    if response.status_code == 200:
        json_data = response.json()

        # Выводим JSON-данные
        print("Полученные JSON-данные:")
        print(json_data)

    else:
        print("Ошибка при получении JSON-файла. Код ответа:", response.status_code)

except requests.exceptions.RequestException as e:
    print("Ошибка при выполнении запроса:", e)
