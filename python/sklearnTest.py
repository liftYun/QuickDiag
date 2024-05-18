from flask import Flask, request, jsonify
import joblib
import numpy as np

app = Flask(__name__)

# 모델과 TF-IDF 변환기 불러오기
svm_model_expanded = joblib.load("svm_model_expanded.joblib")
vectorizer = joblib.load("tfidf_vectorizer.joblib")

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # JSON 형식으로 증상 데이터를 받기
        data = request.get_json(force=True)
        user_input = data.get('symptoms', '')

        if not user_input:
            return jsonify({"error": "No symptoms provided"}), 400

        # 사용자 입력을 리스트 형태로 변환
        user_symptoms = [user_input]

        # TF-IDF 변환을 수행
        X_user_input = vectorizer.transform(user_symptoms)

        # 입력된 증상에 대한 예측된 병명들과 확률
        predicted_proba = svm_model_expanded.predict_proba(X_user_input)
        top_k_indices = np.argsort(predicted_proba[0])[::-1][:5]  # 상위 5개 병명의 인덱스
        top_k_diseases = svm_model_expanded.classes_[top_k_indices]  # 상위 5개 병명

        # 결과를 JSON 형식으로 반환
        result = {"predictions": top_k_diseases.tolist()}
        return jsonify(result)

    except Exception as e:
        return jsonify({"error": str(e)}), 500


if __name__ == '__main__':
    app.run(host='localhost', port=8000, debug=True)
