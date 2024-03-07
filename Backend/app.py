from flask import Flask, request, jsonify

app = Flask(__name__)


@app.route('/')
def hello():
    return "Hello, World!"


tasks = [
    {
        'id': 1,
        'title': u'Buy groceries',
        'description': u'Milk, Cheese, Pizza, Fruit, Tylenol',
        'done': False
    },
    {
        'id': 2,
        'title': u'Learn Python',
        'description': u'Need to find a good Python tutorial on the web',
        'done': False
    }
]


@app.route('/todo/api/v1.0/tasks', methods=['GET'])
def get_tasks():
    return jsonify({'tasks': tasks})


if __name__ == '__main__':
    # from waitress import serve
    # serve(app, host="127.0.0.1", port=8080)
    app.run(debug=True)
