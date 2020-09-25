function isalpha(c) {
    return ("a" <= c && c <= "z") || ("A" <= c && c <= "Z");
}

function isdigit(c) {
    return "0" <= c && c <= "9";
}

function isalnum(c) {
    return isalpha(c) || isdigit(c);
}

function ispunct(c) {
    let x = c.charCodeAt(0);
    return (33 <= x && x <= 47) ||
        (58 <= x && x <= 64) ||
        (91 <= x && x <= 96) ||
        (123 <= x && x <= 126);
}

function isspace(c) {
    return c in {
        " ": true,
        "\n": true,
        "\t": true,
        "\v": true,
        "\r": true,
    };
}

export {
    isalpha,
    isspace,
    isdigit,
    isalnum,
    ispunct,
};
