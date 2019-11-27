from flask import Flask, render_template
app = Flask(__name__)

@app.route('/')
def index():
    return render_template('serial.html')

#@app.route('about')
#def about():
    return 'About 페이지'

if __name__=="__main__":
    app.run()
