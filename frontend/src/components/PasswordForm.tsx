import { useState } from "react";
import type { PasswordRequest } from "../services/api";

interface props {
    onGenerate: (req: PasswordRequest) => void
    loading: boolean;
}

export function PasswordForm({ onGenerate, loading }: props) {
    const [length, setLength] = useState(16);
    const [uppercase, setUppercase] = useState(true);
    const [lowercase, setLowercase] = useState(true);
    const [numbers, setNumbers] = useState(true);
    const [symbols, setSymbols] = useState(true);
    const [excludeAmbiguous, setExcludeAbiguous] = useState(true);
    const [quantity, setQuantity] = useState(1);

    const noneSelected = !uppercase && !lowercase && !numbers && !symbols;

    function handleSubmit() {
        if(noneSelected) return;
        onGenerate({ length, uppercase, lowercase, numbers, symbols, excludeAmbiguous, quantity });
    }

    return (
        <div className="form-card">
            {/* Tamanho */}
            <div className="form-group">
                <label className="form-label">
                    Tamanho <span className="form-value">{length}</span>
                </label>

                <input
                    type="range"
                    onChange={e => setLength(Math.max(8, Math.min(128, Number(e.target.value))))}
                    className="form-range"
                />

                <div className="form-range-labels">
                    <span>8</span><span>128</span>
                </div>
            </div>

            <div className="form-toggles">
                {[
                    { label: "A-Z", value: uppercase, set: setUppercase },
                    { label: "a-z", value: lowercase, set: setLowercase },
                    { label: "0-9", value: numbers, set: setNumbers },
                    { label: "!@#", value: symbols, set: setSymbols },
                    { label: "Sem ambíguos", value: excludeAmbiguous, set: setExcludeAbiguous },
                ].map(({ label, value, set }) => (
                    <button
                        key={label}
                        className={`toggle ${value ? "toggle--on" : ""}`}
                        onClick={() => set(!value)}
                        type="button"
                    >
                        {label}
                    </button>
                ))}
            </div>

            {length < 8 && (
                <p className="form-warn">Tamanho mínimo é 8 caracteres.</p>
            )}
            {noneSelected && (
                <p className="form-warn">Selecione pelo menos um tipo de caractere.</p>
            )}

            {/* Quantidade */}
            <div className="form-group form-group--row">
                <label className="form-label">Quantidade</label>
                <input 
                    type="number" min={1} max={20} value={quantity}
                    onChange={e => setQuantity(Number(e.target.value))}
                    className="form-input-number"
                />
            </div>

            <button
                className="form-btn"
                onClick={handleSubmit}
                disabled={loading || noneSelected}
            >
                {loading ? "Gerando..." : "Gerar senha"}
            </button>
        </div>
    );
}