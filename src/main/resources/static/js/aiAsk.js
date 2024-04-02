// node --version # Should be >= 18
// npm install @google/generative-ai

const {
    GoogleGenerativeAI,
    HarmCategory,
    HarmBlockThreshold,
} = require("@google/generative-ai");

const MODEL_NAME = "gemini-1.0-pro-001";
const API_KEY = "###"; //개인 API 사용필요.

async function runChat(userInput) {
    const genAI = new GoogleGenerativeAI(API_KEY);
    const model = genAI.getGenerativeModel({ model: MODEL_NAME });

    const generationConfig = {
        temperature: 0,
        topK: 1,
        topP: 1,
        maxOutputTokens: 2048,
    };

    const safetySettings = [
        {
            category: HarmCategory.HARM_CATEGORY_HARASSMENT,
            threshold: HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE,
        },
        {
            category: HarmCategory.HARM_CATEGORY_HATE_SPEECH,
            threshold: HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE,
        },
        {
            category: HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT,
            threshold: HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE,
        },
        {
            category: HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT,
            threshold: HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE,
        },
    ];

    /* const chat = model.startChat({
         generationConfig,
         safetySettings,
         history: [
             {
                 role: "user",
                 parts: [{ text: "(머리 증상 : 발열, 오한) 증상을 가지고 있는 질병을 알려줘."}],
             },
             {
                 role: "model",
                 parts: [{ text: "발열과 오한 증상을 가지고 있는 질병은 다음과 같습니다.\n\n" +
                         "1. 감염성 질환\n" +
                         "* 감기\n" +
                         "* 독감\n" +
                         "* 폐렴\n" +
                         "* 요로 감염\n" +
                         "* 수막염\n" +
                         "* 패혈증\n\n" +
                         "2. 비감염성 질환\n" +
                         "* 열사병\n" +
                         "* 열사병\n" +
                         "* 종양 열\n" +
                         "* 아밀로이드증\n" +
                         "* 류마티즘성 관절염\n" +
                         "* 크론병\n\n" +
                         "3. 의약품 관련 열\n" +
                         "* 항생제 (페니실린, 술파 메토작사졸)\n" +
                         "* 항간질제 (페니토인)\n" +
                         "* 비스테로이드성 항염증제 (이부프로펜, 아세트아미노펜)\n\n" +
                         "**기타 원인**\n* 외상\n* 출혈\n* 과도한 운동\n\n" +
                         "정확한 진단을 위해서는 의사의 진찰을 받는 것이 중요합니다. "}],
             },
         ],
     });

     const result = await chat.sendMessage(userInput);  // 사용자 입력을 sendMessage 함수에 전달*/
    const parts = [
        {text: "input: (머리 증상 : 발열, 오한) 증상을 가지고 있는 질병을 알려줘."},
        {text: "output: (머리 증상 : 발열, 오한) 증상을 가지고 있는 질병은 다음과 같습니다.\n\n" +
                "1. 감염성 질환\n" +
                "* 감기\n" +
                "* 독감\n" +
                "* 폐렴\n" +
                "* 요로 감염\n" +
                "* 수막염\n" +
                "* 패혈증\n\n" +
                "2. 비감염성 질환\n" +
                "* 열사병\n" +
                "* 열사병\n" +
                "* 종양 열\n" +
                "* 아밀로이드증\n" +
                "* 류마티즘성 관절염\n" +
                "* 크론병\n\n" +
                "3. 의약품 관련 열\n" +
                "* 항생제 (페니실린, 술파 메토작사졸)\n" +
                "* 항간질제 (페니토인)\n" +
                "* 비스테로이드성 항염증제 (이부프로펜, 아세트아미노펜)\n\n" +
                "**기타 원인**\n* 외상\n* 출혈\n* 과도한 운동\n\n" +
                "정확한 진단을 위해서는 의사의 진찰을 받는 것이 중요합니다. "},
        {text: "input: (허리 증상 : 통증, 저림) 증상을 가지고 있는 질병을 알려줘."},
        {text: "output: (허리 증상 : 통증, 저림) 증상을 가지고 있는 질병은 다음과 같습니다.\n\n" +
                "1. 감염성 질환\n" +
                "* 위장염\n" +
                "* 대장염\n" +
                "* 장티푸스\n" +
                "* 콜레라\n" +
                "* 식중독\n" +
                "\n" +
                "2. 비감염성 질환\n" +
                "* 과민성 대장 증후군\n" +
                "* 염증성 장 질환 (크론병, 궤양성 대장염)\n" +
                "* 대장 폴립\n" +
                "* 대장암\n" +
                "\n" +
                "* 기타 원인\n" +
                "* 음식 알레르기\n" +
                "* 약물 부작용\n" +
                "* 스트레스\n" +
                "\n" +
                "정확한 진단을 위해서는 의사의 진찰을 받는 것이 중요합니다."},
        {text: "input: (어깨 증상 : 통증, 가동범위제한) 증상을 가지고 있는 질병을 알려줘."},
        {text: "output: (어깨 증상 : 통증, 가동범위제한) 증상을 가지고 있는 질병은 다음과 같습니다." +
                "1. 근골격계 질환\n" +
                "* 견갑골 통증 증후군\n" +
                "* 회전근 개 파열\n" +
                "* 냉동견\n" +
                "* 석회성 건염\n" +
                "* 관절염 (류마티즘 관절염, 골관절염)\n" +
                "\n" +
                "2. 신경학적 질환\n" +
                "* 흉곽 출구 증후군\n" +
                "* 신경 압박\n" +
                "* 척수병증\n" +
                "\n" +
                "3. 기타 원인\n" +
                "* 외상\n" +
                "* 과도한 사용\n" +
                "* 자세 불량\n" +
                "\n" +
                "정확한 진단을 위해서는 의사의 진찰을 받는 것이 중요합니다."},
        {text: `input: ${userInput}`},
        {text: "output: "},
    ];

    const result = await model.generateContent({
        contents: [{ role: "user", parts }],
        generationConfig,
        safetySettings,
    });
    const response = result.response;
    console.log(response.text());
}
const userInput = process.argv[2];
runChat(userInput);