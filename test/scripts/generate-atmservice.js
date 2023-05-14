const requestTypes = ["STANDARD", "PRIORITY", "SIGNAL_LOW", "FAILURE_RESTART"];

function generateRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function generateTask() {
    const region = generateRandomInt(1, 50);
    const requestType = requestTypes[generateRandomInt(0, requestTypes.length - 1)];
    const atmId = generateRandomInt(1, 9999);

    return {
        region,
        requestType,
        atmId,
    };
}

function generateTestTasks(numTasks) {
    const tasks = [];
    for (let i = 0; i < numTasks; i++) {
        tasks.push(generateTask());
    }
    return tasks;
}

const testTasks = generateTestTasks(9999);
const jsonString = JSON.stringify(testTasks, null, 4);
console.log(jsonString);
