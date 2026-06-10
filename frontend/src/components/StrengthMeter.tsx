interface Props { password: string }

function calcStrength(password: string): { score: number; label: string; color: string } {
    let score = 0;
    if(password.length >= 12) score++;
    if(password.length >= 20) score++;
    if(/[A-Z]/.test(password)) score++;
    if(/[a-z]/.test(password)) score++;
    if(/[0-9]/.test(password)) score++;
    if(/[^A-Za-z0-9]/.test(password)) score++;

    if(score <= 2) return { score, label: "Fraca", color: "#f85149" }
    if(score <= 4) return { score, label: "Média", color: "#d29922" }
    
    return { score, label: "Forte", color: "#39d353" }
}

export function StrengthMeter({ password }: Props) {
    const { score, label, color } = calcStrength(password);
    const percentage = `${Math.round((score / 6) * 100)}%`

    return (
        <div className="strength">
            <div className="strength-track">
                <div className="strength-fill" style={{ width: percentage, background: color }} />
            </div>

            <span className="strength-label" style={{ color }}>{label}</span>
        </div>
    );
}