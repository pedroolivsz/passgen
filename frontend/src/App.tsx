import { useState } from "react";
import { generatePasswords, type PasswordRequest, type PasswordResponse } from "./services/api";
import { PasswordForm } from "./components/PasswordForm";
export default function App() {
    const [result, setResult] = useState<PasswordResponse | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    async function handleGenerate(request: PasswordRequest) {
        setLoading(true);
        setError(null);

        try {
            const data = await generatePasswords(request);
            setResult(data);
        } catch (e: unknown) {
            setError(e instanceof Error ? e.message : "Erro desconhecido");
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="app">
            <header className="app-header">
                <span className="app-logo">🔐</span>
                <h1 className="app-title">PassGen</h1>
                <p className="app-subtitle">Gerador de senhas seguras</p>
            </header>

            <main className="app-main">
                <PasswordForm onGenerate={handleGenerate} loading={loading}></PasswordForm>
                
            </main>
        </div>
    );
}