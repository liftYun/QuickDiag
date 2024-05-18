import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.svm import SVC
from sklearn.feature_extraction.text import TfidfVectorizer
import joblib

# 엑셀 파일 읽기 (로컬 경로 지정)
df = pd.read_excel('질병별 증상.xlsx', engine='openpyxl')  # openpyxl 엔진 사용
df2 = pd.read_excel('증상별 질병.xlsx', engine='openpyxl')  # openpyxl 엔진 사용
sick = df['질병명']
diag = df['증상']
diag2 = df2['증상']
sick2 = df2['질병명']

# 2022, 2021, 2020 환자수 평균
man = df[['2022_환자수', '2021_환자수', '2020_환자수']].mean(axis=1)

# NA 값 처리
man.fillna(0, inplace=True)

# inf 값을 다른 값으로 대체
man.replace([np.inf, -np.inf], 9999, inplace=True)

# 그 후에 정수로 변환
man = man.round().astype(int)

# 결과를 저장할 딕셔너리 초기화
result = {}

# diag와 sick를 매핑하여 저장
for diag_text, sick_name in zip(diag, sick):
    if isinstance(diag_text, str):  # 문자열인 경우에만 처리
        symptoms = diag_text.split(',')  # 증상을 쉼표로 분리
        for symptom in symptoms:
            if sick_name not in result:
                result[sick_name] = [symptom.strip()]  # 증상을 리스트에 저장
            else:
                result[sick_name].append(symptom.strip())  # 이미 해당 질병이 있다면 새로운 증상 추가

# TF-IDF 벡터화
vectorizer = TfidfVectorizer()
data = [' '.join(symptoms) for symptoms in result.values()]
X = vectorizer.fit_transform(data)
y = [disease for disease in result.keys()]

# result 딕셔너리를 데이터프레임으로 변환
result_df = pd.DataFrame({'질병명': list(result.keys()), '증상': list(result.values())})

# man 리스트에 있는 변수의 수 합계 계산
total_count = sum(man)

# 비율 계산 및 개수 조정
expanded_counts = [round(count / total_count * 150000) for count in man]

# 클래스별 데이터 복제
expanded_data = pd.DataFrame(columns=['질병명', '증상'])
for idx, (disease, symptoms) in enumerate(result.items()):
    count = expanded_counts[idx]
    expanded_data = pd.concat([expanded_data, pd.DataFrame({'질병명': [disease] * count, '증상': [symptoms] * count})])

# 데이터 결합
result_expanded = pd.concat([result_df, expanded_data])

# SVM 모델을 다시 훈련하기 위해 데이터를 TF-IDF 벡터화
X_expanded = vectorizer.transform([' '.join(symptoms) for symptoms in result_expanded['증상']])
y_expanded = result_expanded['질병명']

# 훈련 및 테스트 데이터 분할
X_train_expanded, X_test_expanded, y_train_expanded, y_test_expanded = train_test_split(X_expanded, y_expanded, test_size=0.2, random_state=42)

# SVM 모델 재훈련
svm_model_expanded = SVC(kernel='linear', probability=True)  # 확률을 제공하는 설정으로 모델 초기화
svm_model_expanded.fit(X_train_expanded, y_train_expanded)

# 모델과 TF-IDF 변환기 저장
joblib.dump(svm_model_expanded, "svm_model_expanded.joblib")
joblib.dump(vectorizer, "tfidf_vectorizer.joblib")
