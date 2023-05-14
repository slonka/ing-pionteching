function generateRandomAccountNumber() {
    let accountNumber = '';
    for (let i = 0; i < 26; i++) {
        accountNumber += Math.floor(Math.random() * 10);
    }
    return accountNumber;
}

function generateRandomTransaction() {
    return {
        debitAccount: generateRandomAccountNumber(),
        creditAccount: generateRandomAccountNumber(),
        amount: parseFloat((Math.random() * 10000).toFixed(2))
    };
}

function generateTestTransactions(numTransactions) {
    const transactions = [];
    for (let i = 0; i < numTransactions; i++) {
        transactions.push(generateRandomTransaction());
    }
    return transactions;
}

const testTransactions = generateTestTransactions(100000);
const jsonString = JSON.stringify(testTransactions, null, 4);
console.log(jsonString);
